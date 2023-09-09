package com.gumu.onboarding_presentation.age

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.R
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.core.usecase.FilterOutDigits
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
class AgeViewModel @Inject constructor(
    private val dataStore: DataPreferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    private val _age = MutableStateFlow("0")
    val age = _age.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun onAgeChange(age: String) {
        if (age.length < 3) {
            _age.update { filterOutDigits(age) }
        }
    }

    private fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = _age.value.toIntOrNull()?.takeIf { it > 0 } ?: run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_age_cant_be_empty))
                )
                return@launch
            }

            dataStore.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Navigate(Screen.Height.route))
        }
    }

    fun onEvent(event: AgeEvent) {
        when (event) {
            is AgeEvent.AgeChange -> onAgeChange(event.age)
            AgeEvent.NextClick -> onNextClick()
        }
    }
}
