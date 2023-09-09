package com.gumu.onboarding_presentation.nutrient_goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.core.usecase.FilterOutDigits
import com.gumu.core_ui.navigation.Screen
import com.gumu.core_ui.util.UiEvent
import com.gumu.onboarding_domain.usecase.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val dataStore: DataPreferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
) : ViewModel() {
    private val _uiState = MutableStateFlow(NutrientGoalState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun onCarbsRatioChange(ratio: String) {
        if (ratio.length < 3) {
            _uiState.update { it.copy(carbsRatio = filterOutDigits(ratio)) }
        }
    }

    private fun onProteinRatioChange(ratio: String) {
        if (ratio.length < 3) {
            _uiState.update { it.copy(proteinRatio = filterOutDigits(ratio)) }
        }
    }

    private fun onFatRatioChange(ratio: String) {
        if (ratio.length < 3) {
            _uiState.update { it.copy(fatRatio = filterOutDigits(ratio)) }
        }
    }

    private fun onNextClick() {
        val result = _uiState.value.let {
            validateNutrients(it.carbsRatio, it.proteinRatio, it.fatRatio)
        }

        viewModelScope.launch {
            when (result) {
                is ValidateNutrients.Result.Success -> {
                    dataStore.saveCarbRatio(result.carbsRatio)
                    dataStore.saveProteinRatio(result.proteinRatio)
                    dataStore.saveFatRatio(result.fatRatio)
                    _uiEvent.send(UiEvent.Navigate(Screen.TrackerOverview.route))
                }
                is ValidateNutrients.Result.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackbar(result.message))
                }
            }
        }
    }

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.CarbsChange -> onCarbsRatioChange(event.ratio)
            is NutrientGoalEvent.ProteinChange -> onProteinRatioChange(event.ratio)
            is NutrientGoalEvent.FatChange -> onFatRatioChange(event.ratio)
            NutrientGoalEvent.NextClick -> onNextClick()
        }
    }
}
