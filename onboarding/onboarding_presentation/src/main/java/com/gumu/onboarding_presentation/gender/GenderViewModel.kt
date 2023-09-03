package com.gumu.onboarding_presentation.gender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.domain.model.Gender
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
class GenderViewModel @Inject constructor(
    private val dataStore: DataPreferences
) : ViewModel() {
    private val _selectedGender: MutableStateFlow<Gender> = MutableStateFlow(Gender.Male)
    val selectedGender = _selectedGender.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun onGenderClick(gender: Gender) {
        _selectedGender.update { gender }
    }

    private fun onNextClick() {
        viewModelScope.launch {
            dataStore.saveGender(_selectedGender.value)
            _uiEvent.send(UiEvent.Navigate(Screen.Age.route))
        }
    }

    fun onEvent(event: GenderEvent) {
        when (event) {
            is GenderEvent.GenderClick -> onGenderClick(event.gender)
            is GenderEvent.NextClick -> onNextClick()
        }
    }
}
