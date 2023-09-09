package com.gumu.onboarding_presentation.nutrient_goal

sealed class NutrientGoalEvent {
    data class CarbsChange(val ratio: String) : NutrientGoalEvent()
    data class ProteinChange(val ratio: String) : NutrientGoalEvent()
    data class FatChange(val ratio: String) : NutrientGoalEvent()
    object NextClick : NutrientGoalEvent()
}
