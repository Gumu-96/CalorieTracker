package com.gumu.onboarding_presentation.weight

sealed class WeightEvent {
    data class WeightChange(val weight: String) : WeightEvent()
    object NextClick : WeightEvent()
}
