package com.gumu.onboarding_presentation.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.domain.model.GoalType
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.core_ui.navigation.Screen
import com.gumu.core_ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val dataStore: DataPreferences
) : ViewModel() {
    private val _selectedGoal: MutableStateFlow<GoalType> = MutableStateFlow(GoalType.KeepWeight)
    val selectedGoal = _selectedGoal.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun onGoalClick(goal: GoalType) {
        _selectedGoal.update { goal }
    }

    private fun onNextClick() {
        viewModelScope.launch {
            dataStore.saveGoalType(_selectedGoal.value)
            _uiEvent.send(UiEvent.Navigate(Screen.NutrientGoal.route))
        }
    }

    fun onEvent(event: GoalEvent) {
        when (event) {
            is GoalEvent.GoalClick -> onGoalClick(event.goal)
            is GoalEvent.NextClick -> onNextClick()
        }
    }
}
