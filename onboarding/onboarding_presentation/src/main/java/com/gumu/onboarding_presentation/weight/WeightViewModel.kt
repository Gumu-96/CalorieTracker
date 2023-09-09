package com.gumu.onboarding_presentation.weight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.R
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.core.util.UiText
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
class WeightViewModel @Inject constructor(
    private val dataStore: DataPreferences
) : ViewModel() {
    private val _weight = MutableStateFlow("0")
    val weight = _weight.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun onWeightChange(weight: String) {
        if (weight.length <= 5) {
            _weight.update { weight }
        }
    }

    private fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = _weight.value.toFloatOrNull()?.takeIf { it > 0 } ?: run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_weight_cant_be_empty))
                )
                return@launch
            }

            dataStore.saveWeight(weightNumber)
            _uiEvent.send(UiEvent.Navigate(Screen.Activity.route))
        }
    }

    fun onEvent(event: WeightEvent) {
        when (event) {
            is WeightEvent.WeightChange -> onWeightChange(event.weight)
            WeightEvent.NextClick -> onNextClick()
        }
    }
}
