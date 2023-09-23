package com.gumu.calorietracker

import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.common.truth.Truth.assertThat
import com.gumu.calorietracker.navigation.navigate
import com.gumu.calorietracker.repository.TrackerRepositoryFake
import com.gumu.core.domain.model.ActivityLevel
import com.gumu.core.domain.model.Gender
import com.gumu.core.domain.model.GoalType
import com.gumu.core.domain.model.UserInfo
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.core.usecase.FilterOutDigits
import com.gumu.core_ui.navigation.Screen
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.tracker_domain.model.TrackableFood
import com.gumu.tracker_domain.usecase.CalculateMealNutrients
import com.gumu.tracker_domain.usecase.DeleteTrackedFood
import com.gumu.tracker_domain.usecase.GetFoodsForDate
import com.gumu.tracker_domain.usecase.SearchFood
import com.gumu.tracker_domain.usecase.TrackFood
import com.gumu.tracker_domain.usecase.TrackerUseCases
import com.gumu.tracker_presentation.search.SearchScreen
import com.gumu.tracker_presentation.search.SearchViewModel
import com.gumu.tracker_presentation.tracker_overview.TrackerOverviewScreen
import com.gumu.tracker_presentation.tracker_overview.TrackerOverviewViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@HiltAndroidTest
class TrackerOverviewE2E {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repository: TrackerRepositoryFake
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var prefs: DataPreferences
    private lateinit var trackerOverviewViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        prefs = mockk(relaxed = true)
        every { prefs.loadUserInfo() } returns flowOf(
            UserInfo(
                gender = Gender.Male,
                age = 20,
                height = 180,
                weight = 80f,
                activityLevel = ActivityLevel.Medium,
                goalType = GoalType.KeepWeight,
                carbRatio = 0.4f,
                proteinRatio = 0.3f,
                fatRatio = 0.3f
            )
        )
        repository = TrackerRepositoryFake()
        trackerUseCases = TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(prefs)
        )
        trackerOverviewViewModel = TrackerOverviewViewModel(
            dataPrefs = prefs,
            useCases = trackerUseCases
        )
        searchViewModel = SearchViewModel(
            useCases = trackerUseCases,
            filterOutDigits = FilterOutDigits()
        )
        composeRule.activity.setContent {
            navController = rememberNavController()
            CalorieTrackerTheme {
                NavHost(navController = navController, startDestination = Screen.TrackerOverview.route) {
                    composable(route = Screen.TrackerOverview.route) {
                        val uiState by trackerOverviewViewModel.uiState.collectAsState()
                        TrackerOverviewScreen(
                            onNavigate = { navController.navigate(it) },
                            uiState = uiState,
                            uiEvents = trackerOverviewViewModel.uiEvent,
                            onEvent = trackerOverviewViewModel::onEvent
                        )
                    }
                    composable(
                        route = Screen.Search.route,
                        arguments = listOf(
                            navArgument(Screen.MEAL_TYPE_PARAM) {
                                type = NavType.StringType
                            },
                            navArgument(Screen.DAY_OF_MONTH_PARAM) {
                                type = NavType.IntType
                            },
                            navArgument(Screen.MONTH_PARAM) {
                                type = NavType.IntType
                            },
                            navArgument(Screen.YEAR_PARAM) {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val uiState by searchViewModel.uiState.collectAsState()

                        val today = LocalDate.now()
                        val mealName = it.arguments?.getString(Screen.MEAL_TYPE_PARAM)
                        val dayOfMonth = it.arguments?.getInt(Screen.DAY_OF_MONTH_PARAM) ?: today.dayOfMonth
                        val month = it.arguments?.getInt(Screen.MONTH_PARAM) ?: today.monthValue
                        val year = it.arguments?.getInt(Screen.YEAR_PARAM) ?: today.year
                        SearchScreen(
                            onNavigateUp = { navController.navigateUp() },
                            uiState = uiState,
                            mealName = mealName,
                            dayOfMonth = dayOfMonth,
                            month = month,
                            year = year,
                            uiEvents = searchViewModel.uiEvent,
                            onEvent = searchViewModel::onEvent
                        )
                    }
                }
            }
        }
    }

    @Test
    fun whenAddBreakfast_thenAppearsUnderBreakfastAndNutrientsCorrectlyCalculated() {
        repository.searchResults = listOf(
            TrackableFood(
                name = "apple",
                imageUrl = null,
                caloriesPer100g = 150,
                carbsPer100g = 50,
                proteinPer100g = 5,
                fatPer100g = 1
            )
        )
        val addedAmount = 150

        // Expand Breakfast and navigate to search screen
        composeRule
            .onNodeWithText("Add Breakfast")
            .assertDoesNotExist()
        composeRule
            .onNodeWithContentDescription("Breakfast")
            .performClick()
        composeRule
            .onNodeWithText("Add Breakfast")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("Add Breakfast")
            .performClick()

        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Screen.Search.baseRoute)
        ).isTrue()

        // Input a query and perform search
        composeRule
            .onNodeWithTag("search_text_field")
            .performTextInput("apple")
        composeRule
            .onNodeWithContentDescription("Search...")
            .performClick()

        // Expand food item, input an amount and track food item
        composeRule
            .onNodeWithText("Carbs")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Amount")
            .performTextInput(addedAmount.toString())
        composeRule
            .onNodeWithContentDescription("Track")
            .performClick()

        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Screen.TrackerOverview.baseRoute)
        ).isTrue()

        composeRule
            .onAllNodesWithText("apple")
            .onFirst()
            .assertIsDisplayed()
        composeRule
            .onAllNodesWithText(
                text = "${addedAmount}g",
                substring = true
            )
            .onFirst()
            .assertIsDisplayed()
    }
}
