package com.gumu.tracker_presentation.tracker_overview

import com.gumu.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    object NextDayClick : TrackerOverviewEvent()
    object PreviousDayClick : TrackerOverviewEvent()
    data class ToggleMealClick(val meal: Meal) : TrackerOverviewEvent()
    data class DeleteTrackedFoodClick(val food: TrackedFood) : TrackerOverviewEvent()
    data class AddFoodClick(val meal: Meal) : TrackerOverviewEvent()
}
