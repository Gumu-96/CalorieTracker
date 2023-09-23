package com.gumu.tracker_domain.usecase

import com.google.common.truth.Truth.assertThat
import com.gumu.core.domain.model.ActivityLevel
import com.gumu.core.domain.model.Gender
import com.gumu.core.domain.model.GoalType
import com.gumu.core.domain.model.UserInfo
import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.tracker_domain.model.MealType
import com.gumu.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {
    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setup() {
        val prefs = mockk<DataPreferences>(relaxed = true)
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
        calculateMealNutrients = CalculateMealNutrients(prefs)
    }

    @Test
    fun givenUserInfo_whenCalculatingMealNutrients_thenCaloriesCalculatedCorrectlyForBreakfast() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "food$it",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                type = listOf(
                    MealType.Breakfast,
                    MealType.Lunch,
                    MealType.Dinner,
                    MealType.Snack,
                ).random(),
                imgUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = runBlocking { calculateMealNutrients(trackedFoods) }

        val breakfastCalories = result.mealNutrients.values
            .filter { it.type == MealType.Breakfast }
            .sumOf { it.calories }
        val expectedCalories = trackedFoods
            .filter { it.type == MealType.Breakfast }
            .sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)
    }

    @Test
    fun givenUserInfo_whenCalculatingMealNutrients_thenCarbsCalculatedCorrectlyForDinner() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "food$it",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                type = listOf(
                    MealType.Breakfast,
                    MealType.Lunch,
                    MealType.Dinner,
                    MealType.Snack,
                ).random(),
                imgUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = runBlocking { calculateMealNutrients(trackedFoods) }

        val dinnerCarbs = result.mealNutrients.values
            .filter { it.type == MealType.Dinner }
            .sumOf { it.carbs }
        val expectedCarbs = trackedFoods
            .filter { it.type == MealType.Dinner }
            .sumOf { it.carbs }

        assertThat(dinnerCarbs).isEqualTo(expectedCarbs)
    }
}
