package com.gumu.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gumu.core_ui.navigation.Screen
import com.gumu.onboarding_presentation.gender.GenderScreen
import com.gumu.onboarding_presentation.gender.GenderViewModel
import com.gumu.onboarding_presentation.welcome.WelcomeScreen

@Composable
fun CalorieTrackerNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        // Onboarding
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(onNavigate = { navController.navigate(it) })
        }
        composable(route = Screen.Gender.route) {
            val viewModel: GenderViewModel = hiltViewModel()
            val selectedGender by viewModel.selectedGender.collectAsState()
            GenderScreen(
                onNavigate = { navController.navigate(it) },
                selectedGender = selectedGender,
                uiEvents = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.Age.route) {
        }
        composable(route = Screen.Height.route) {
        }
        composable(route = Screen.Weight.route) {
        }
        composable(route = Screen.NutrientGoal.route) {
        }
        composable(route = Screen.Activity.route) {
        }
        composable(route = Screen.Goal.route) {
        }

        // Tracker
        composable(route = Screen.TrackerOverview.route) {
        }
        composable(route = Screen.Search.route) {
        }
    }
}
