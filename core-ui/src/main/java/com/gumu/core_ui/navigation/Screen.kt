package com.gumu.core_ui.navigation

sealed class Screen(val route: String) {
    // Onboarding
    object Welcome : Screen("welcome_screen")
    object Age : Screen("age_screen")
    object Gender : Screen("gender_screen")
    object Height : Screen("height_screen")
    object Weight : Screen("weight_screen")
    object NutrientGoal : Screen("nutrient_goal_screen")
    object Activity : Screen("activity_screen")
    object Goal : Screen("goal_screen")

    // Tracker
    object TrackerOverview : Screen("tracker_overview_screen")
    object Search : Screen("search_screen")
}
