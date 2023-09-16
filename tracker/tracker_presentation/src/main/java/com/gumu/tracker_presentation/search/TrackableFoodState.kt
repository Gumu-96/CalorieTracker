package com.gumu.tracker_presentation.search

import com.gumu.tracker_domain.model.TrackableFood

data class TrackableFoodState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)
