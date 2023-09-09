package com.gumu.onboarding_presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.domain.model.ActivityLevel
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
class ActivityViewModel @Inject constructor(
    private val dataStore: DataPreferences
) : ViewModel() {
    private val _selectedActivity: MutableStateFlow<ActivityLevel> = MutableStateFlow(ActivityLevel.Medium)
    val selectedActivity = _selectedActivity.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun onActivityClick(activity: ActivityLevel) {
        _selectedActivity.update { activity }
    }

    private fun onNextClick() {
        viewModelScope.launch {
            dataStore.saveActivityLevel(_selectedActivity.value)
            _uiEvent.send(UiEvent.Navigate(Screen.Goal.route))
        }
    }

    fun onEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.ActivityClick -> onActivityClick(event.activity)
            is ActivityEvent.NextClick -> onNextClick()
        }
    }
}
