package com.gumu.onboarding_presentation.gender

import com.gumu.core.domain.model.Gender

sealed class GenderEvent {
    data class GenderClick(val gender: Gender) : GenderEvent()
    object NextClick : GenderEvent()
}
