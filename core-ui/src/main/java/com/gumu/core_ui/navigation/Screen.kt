package com.gumu.core_ui.navigation

sealed class Screen(val route: String) {
    private val baseRoute: String =
        route.substring(0, route.indexOf('/').takeIf { it > -1 } ?: route.length)

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
    object Search : Screen("search_screen/{$MEAL_TYPE_PARAM}/{$DAY_OF_MONTH_PARAM}/{$MONTH_PARAM}/{$YEAR_PARAM}")

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(baseRoute)
            args.forEach {
                append("/$it")
            }
        }
    }

    companion object {
        const val MEAL_TYPE_PARAM = "meal_type"
        const val DAY_OF_MONTH_PARAM = "day_of_month"
        const val MONTH_PARAM = "month"
        const val YEAR_PARAM = "year"
    }
}
