package com.gumu.onboarding_presentation.height

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.core.usecase.FilterOutDigits
import com.gumu.core_ui.R
import com.gumu.core_ui.navigation.Screen
import com.gumu.core_ui.util.UiEvent
import com.gumu.core_ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val dataStore: DataPreferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    private val _height = MutableStateFlow("0")
    val height = _height.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun onHeightChange(height: String) {
        if (height.length <= 3) {
            _height.update { filterOutDigits(height) }
        }
    }

    private fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = _height.value.toIntOrNull()?.takeIf { it > 0 } ?: run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_height_cant_be_empty))
                )
                return@launch
            }

            dataStore.saveHeight(heightNumber)
            _uiEvent.send(UiEvent.Navigate(Screen.Weight.route))
        }
    }

    fun onEvent(event: HeightEvent) {
        when (event) {
            is HeightEvent.HeightChange -> onHeightChange(event.height)
            HeightEvent.NextClick -> onNextClick()
        }
    }
}
