package com.gumu.tracker_presentation.tracker_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.core_ui.navigation.Screen
import com.gumu.core_ui.util.UiEvent
import com.gumu.tracker_domain.model.TrackedFood
import com.gumu.tracker_domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    private val dataPrefs: DataPreferences,
    private val useCases: TrackerUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(TrackerOverviewState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    init {
        viewModelScope.launch {
            dataPrefs.saveShouldShowOnboarding(false)
        }
    }

    private fun onAddFoodClick(meal: Meal) {
        viewModelScope.launch {
            _uiState.value.let {
                _uiEvent.send(
                    UiEvent.Navigate(
                        Screen.Search.routeWithArgs(
                            meal.mealType.name,
                            it.date.dayOfMonth.toString(),
                            it.date.monthValue.toString(),
                            it.date.year.toString(),
                        )
                    )
                )
            }
        }
    }

    private fun onDeleteTrackedFoodClick(food: TrackedFood) {
        viewModelScope.launch {
            useCases.deleteTrackedFood(food)
            refreshFoods()
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = useCases
            .getFoodsForDate(_uiState.value.date)
            .onEach { foods ->
                val nutrientsResult = useCases.calculateMealNutrients(foods)
                _uiState.update { currentState ->
                    currentState.copy(
                        totalCarbs = nutrientsResult.totalCarbs,
                        totalProtein = nutrientsResult.totalProtein,
                        totalFat = nutrientsResult.totalFat,
                        totalCalories = nutrientsResult.totalCalories,
                        carbsGoal = nutrientsResult.carbsGoal,
                        proteinGoal = nutrientsResult.proteinGoal,
                        fatGoal = nutrientsResult.fatGoal,
                        caloriesGoal = nutrientsResult.caloriesGoal,
                        trackedFoods = foods,
                        meals = currentState.meals.map {
                            val nutrientsForMeal =
                                nutrientsResult.mealNutrients[it.mealType]
                                    ?: return@map it.copy(
                                        carbs = 0,
                                        protein = 0,
                                        fat = 0,
                                        calories = 0
                                    )
                            it.copy(
                                carbs = nutrientsForMeal.carbs,
                                protein = nutrientsForMeal.protein,
                                fat = nutrientsForMeal.fat,
                                calories = nutrientsForMeal.calories,
                            )
                        }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onToggleMealClick(meal: Meal) {
        _uiState.update { currentState ->
            currentState.copy(
                meals = currentState.meals.map {
                    if (it.name == meal.name) {
                        it.copy(isExpanded = it.isExpanded.not())
                    } else it
                }
            )
        }
    }

    private fun onNextDayClick() {
        _uiState.update { it.copy(date = it.date.plusDays(1)) }
        refreshFoods()
    }

    private fun onPreviousDayClick() {
        _uiState.update { it.copy(date = it.date.minusDays(1)) }
        refreshFoods()
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is TrackerOverviewEvent.AddFoodClick -> onAddFoodClick(event.meal)
            is TrackerOverviewEvent.DeleteTrackedFoodClick -> onDeleteTrackedFoodClick(event.food)
            TrackerOverviewEvent.NextDayClick -> onNextDayClick()
            TrackerOverviewEvent.PreviousDayClick -> onPreviousDayClick()
            is TrackerOverviewEvent.ToggleMealClick -> onToggleMealClick(event.meal)
        }
    }
}
