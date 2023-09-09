package com.gumu.onboarding_presentation.activity

import com.gumu.core.domain.model.ActivityLevel

sealed class ActivityEvent {
    data class ActivityClick(val activity: ActivityLevel) : ActivityEvent()
    object NextClick : ActivityEvent()
}
