package com.gumu.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gumu.core_ui.navigation.Screen
import com.gumu.onboarding_presentation.age.AgeScreen
import com.gumu.onboarding_presentation.age.AgeViewModel
import com.gumu.onboarding_presentation.gender.GenderScreen
import com.gumu.onboarding_presentation.gender.GenderViewModel
import com.gumu.onboarding_presentation.height.HeightScreen
import com.gumu.onboarding_presentation.height.HeightViewModel
import com.gumu.onboarding_presentation.weight.WeightScreen
import com.gumu.onboarding_presentation.weight.WeightViewModel
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
            val viewModel: AgeViewModel = hiltViewModel()
            val age by viewModel.age.collectAsState()
            AgeScreen(
                onNavigate = { navController.navigate(it) },
                age = age,
                uiEvents = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.Height.route) {
            val viewModel: HeightViewModel = hiltViewModel()
            val height by viewModel.height.collectAsState()
            HeightScreen(
                onNavigate = { navController.navigate(it) },
                height = height,
                uiEvents = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.Weight.route) {
            val viewModel: WeightViewModel = hiltViewModel()
            val weight by viewModel.weight.collectAsState()
            WeightScreen(
                onNavigate = { navController.navigate(it) },
                weight = weight,
                uiEvents = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
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
