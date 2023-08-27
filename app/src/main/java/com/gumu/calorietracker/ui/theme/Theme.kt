package com.gumu.calorietracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BrightGreen,
    onPrimary = Color.White,
    secondary = Orange,
    onSecondary = Color.White,
    tertiary = CarbColor,
    background = MediumGray,
    onBackground = TextWhite,
    error = FatColor,
    surface = LightGray,
    onSurface = TextWhite,
    surfaceVariant = DarkGreen,
    onSurfaceVariant = TextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = BrightGreen,
    onPrimary = Color.White,
    secondary = Orange,
    onSecondary = Color.White,
    tertiary = CarbColor,
    background = Color.White,
    onBackground = DarkGray,
    error = FatColor,
    surface = Color.White,
    onSurface = DarkGray,
    surfaceVariant = DarkGreen,
    onSurfaceVariant = TextWhite
)

@Composable
fun CalorieTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
