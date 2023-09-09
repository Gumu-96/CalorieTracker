package com.gumu.onboarding_presentation.goal

import com.gumu.core.domain.model.GoalType

sealed class GoalEvent {
    data class GoalClick(val goal: GoalType) : GoalEvent()
    object NextClick : GoalEvent()
}
