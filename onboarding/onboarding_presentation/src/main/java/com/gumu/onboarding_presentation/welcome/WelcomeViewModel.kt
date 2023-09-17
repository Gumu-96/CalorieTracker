package com.gumu.onboarding_presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.core_ui.navigation.Screen
import com.gumu.core_ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val dataPreferences: DataPreferences
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (dataPreferences.getShouldShowOnboarding().first().not()) {
                _uiEvent.send(UiEvent.Navigate(Screen.TrackerOverview.route))
            }
        }
    }
}
