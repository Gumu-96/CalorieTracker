package com.gumu.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.CarbColor
import com.gumu.core_ui.theme.FatColor
import com.gumu.core_ui.theme.Orange

@Composable
fun NutrientsBar(
    modifier: Modifier = Modifier,
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val caloriesExceedColor = MaterialTheme.colorScheme.error
    val carbWidthRatio = remember { Animatable(0f) }
    val proteinWidthRatio = remember { Animatable(0f) }
    val fatWidthRatio = remember { Animatable(0f) }

    LaunchedEffect(carbs) {
        carbWidthRatio.animateTo(
            targetValue = ((carbs * 4f) / calorieGoal)
        )
    }
    LaunchedEffect(protein) {
        proteinWidthRatio.animateTo(
            targetValue = ((protein * 4f) / calorieGoal)
        )
    }
    LaunchedEffect(fat) {
        fatWidthRatio.animateTo(
            targetValue = ((fat * 9f) / calorieGoal)
        )
    }

    Canvas(modifier = modifier) {
        if (calories <= calorieGoal) {
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinWidth = proteinWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width

            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = FatColor,
                size = Size(
                    width = carbsWidth + proteinWidth + fatWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = Orange,
                size = Size(
                    width = carbsWidth + proteinWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = CarbColor,
                size = Size(
                    width = carbsWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
        } else {
            drawRoundRect(
                color = caloriesExceedColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NutrientsBarPreview() {
    CalorieTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.primary) {
            NutrientsBar(
                carbs = 200,
                protein = 100,
                fat = 50,
                calories = 750,
                calorieGoal = 1500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(30.dp)
            )
        }
    }
}
