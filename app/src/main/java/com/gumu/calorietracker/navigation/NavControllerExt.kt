package com.gumu.calorietracker.navigation

import androidx.navigation.NavController
import com.gumu.core_ui.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    navigate(event.route)
}
