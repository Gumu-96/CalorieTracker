package com.gumu.tracker_presentation.search

import com.gumu.tracker_domain.model.MealType
import com.gumu.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class QueryChange(val query: String) : SearchEvent()
    object Search : SearchEvent()
    data class ToggleTrackableFood(val food: TrackableFood) : SearchEvent()
    data class AmountForFoodChange(val amount: String, val food: TrackableFood) : SearchEvent()
    data class TrackFoodClick(
        val food: TrackableFoodState,
        val type: MealType,
        val date: LocalDate
    ) : SearchEvent()
    data class SearchFocusChange(val isFocused: Boolean) : SearchEvent()
}
