package com.gumu.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gumu.calorietracker.navigation.CalorieTrackerNavigation
import com.gumu.core_ui.theme.CalorieTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                CalorieTrackerNavigation()
            }
        }
    }
}
