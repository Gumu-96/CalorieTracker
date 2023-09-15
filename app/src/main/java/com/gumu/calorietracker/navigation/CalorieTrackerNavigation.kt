package com.gumu.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gumu.core_ui.navigation.Screen
import com.gumu.onboarding_presentation.activity.ActivityScreen
import com.gumu.onboarding_presentation.activity.ActivityViewModel
import com.gumu.onboarding_presentation.age.AgeScreen
import com.gumu.onboarding_presentation.age.AgeViewModel
import com.gumu.onboarding_presentation.gender.GenderScreen
import com.gumu.onboarding_presentation.gender.GenderViewModel
import com.gumu.onboarding_presentation.goal.GoalScreen
import com.gumu.onboarding_presentation.goal.GoalViewModel
import com.gumu.onboarding_presentation.height.HeightScreen
import com.gumu.onboarding_presentation.height.HeightViewModel
import com.gumu.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.gumu.onboarding_presentation.nutrient_goal.NutrientGoalViewModel
import com.gumu.onboarding_presentation.weight.WeightScreen
import com.gumu.onboarding_presentation.weight.WeightViewModel
import com.gumu.onboarding_presentation.welcome.WelcomeScreen
import com.gumu.tracker_presentation.tracker_overview.TrackerOverviewScreen
import com.gumu.tracker_presentation.tracker_overview.TrackerOverviewViewModel

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
        composable(route = Screen.Activity.route) {
            val viewModel: ActivityViewModel = hiltViewModel()
            val selectedActivity by viewModel.selectedActivity.collectAsState()
            ActivityScreen(
                onNavigate = { navController.navigate(it) },
                selectedActivity = selectedActivity,
                uiEvents = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.Goal.route) {
            val viewModel: GoalViewModel = hiltViewModel()
            val selectedGoal by viewModel.selectedGoal.collectAsState()
            GoalScreen(
                onNavigate = { navController.navigate(it) },
                selectedGoal = selectedGoal,
                uiEvents = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.NutrientGoal.route) {
            val viewModel: NutrientGoalViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            NutrientGoalScreen(
                onNavigate = { navController.navigate(it) },
                uiState = uiState,
                uiEvents = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }

        // Tracker
        composable(route = Screen.TrackerOverview.route) {
            val viewModel: TrackerOverviewViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            TrackerOverviewScreen(
                onNavigate = { navController.navigate(it) },
                uiState = uiState,
                uiEvents = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.Search.route) {
        }
    }
}
