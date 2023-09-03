package com.gumu.onboarding_presentation.age

sealed class AgeEvent {
    data class AgeChange(val age: String) : AgeEvent()
    object NextClick : AgeEvent()
}
