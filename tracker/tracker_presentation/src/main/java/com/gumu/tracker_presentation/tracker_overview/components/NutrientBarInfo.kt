package com.gumu.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gumu.core.R
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.tracker_presentation.components.UnitDisplay

@Composable
fun NutrientBarInfo(
    value: Int,
    goal: Int,
    name: String,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val goalExceedColor = MaterialTheme.colorScheme.error
    val angleRatio = remember { Animatable(0f) }

    LaunchedEffect(key1 = value) {
        angleRatio.animateTo(
            targetValue = if (goal > 0) value / goal.toFloat() else 0f,
            animationSpec = tween(300)
        )
    }

    Box(
        modifier = modifier.padding(strokeWidth / 2),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            drawArc(
                color = if (value <= goal) backgroundColor else goalExceedColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size,
                style = Stroke(width = strokeWidth.toPx())
            )
            if (value <= goal) {
                drawArc(
                    color = color,
                    startAngle = 90f,
                    sweepAngle = 360f * angleRatio.value,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UnitDisplay(
                amount = value,
                unit = stringResource(id = R.string.grams),
                amountColor = if (value <= goal) MaterialTheme.colorScheme.onPrimary else goalExceedColor,
                unitColor = if (value <= goal) MaterialTheme.colorScheme.onPrimary else goalExceedColor
            )
            Text(
                text = name,
                color = if (value <= goal) MaterialTheme.colorScheme.onPrimary else goalExceedColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview
@Composable
private fun NutrientBarInfoPreview() {
    CalorieTrackerTheme {
        NutrientBarInfo(
            value = 35,
            goal = 200,
            name = "Fat",
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(100.dp)
        )
    }
}
