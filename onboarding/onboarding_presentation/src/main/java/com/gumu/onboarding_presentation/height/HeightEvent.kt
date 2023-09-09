package com.gumu.onboarding_presentation.height

sealed class HeightEvent {
    data class HeightChange(val height: String) : HeightEvent()
    object NextClick : HeightEvent()
}
