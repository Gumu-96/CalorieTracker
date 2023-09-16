package com.gumu.tracker_presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.R
import com.gumu.core.usecase.FilterOutDigits
import com.gumu.core.util.UiText
import com.gumu.core_ui.util.UiEvent
import com.gumu.tracker_domain.model.MealType
import com.gumu.tracker_domain.model.TrackableFood
import com.gumu.tracker_domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    private fun onAmountForFoodChange(amount: String, food: TrackableFood) {
        _uiState.update { state ->
            state.copy(
                trackableFoods = state.trackableFoods.map {
                    if (it.food == food) it.copy(amount = filterOutDigits(amount)) else it
                }
            )
        }
    }

    private fun onToggleTrackableFood(food: TrackableFood) {
        _uiState.update { state ->
            state.copy(
                trackableFoods = state.trackableFoods.map {
                    if (it.food == food) it.copy(isExpanded = it.isExpanded.not()) else it
                }
            )
        }
    }

    private fun onSearchFocusChange(isFocused: Boolean) {
        _uiState.update { it.copy(isHintVisible = isFocused.not() && it.query.isBlank()) }
    }

    private fun onTrackFoodClick(foodState: TrackableFoodState, type: MealType, date: LocalDate) {
        viewModelScope.launch {
            useCases.trackFood(
                food = foodState.food,
                amount = foodState.amount.toIntOrNull()?.takeIf { it > 0 } ?: return@launch,
                type = type,
                date = date
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    private fun onSearch() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true, trackableFoods = emptyList()) }
            useCases
                .searchFood(_uiState.value.query)
                .onSuccess { foods ->
                    _uiState.update { state ->
                        state.copy(
                            trackableFoods = foods.map { TrackableFoodState(it) },
                            isSearching = false,
                            query = ""
                        )
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isSearching = false) }
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_something_went_wrong))
                    )
                }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.AmountForFoodChange -> onAmountForFoodChange(event.amount, event.food)
            is SearchEvent.QueryChange -> onQueryChange(event.query)
            SearchEvent.Search -> onSearch()
            is SearchEvent.SearchFocusChange -> onSearchFocusChange(event.isFocused)
            is SearchEvent.ToggleTrackableFood -> onToggleTrackableFood(event.food)
            is SearchEvent.TrackFoodClick -> onTrackFoodClick(event.food, event.type, event.date)
        }
    }
}
