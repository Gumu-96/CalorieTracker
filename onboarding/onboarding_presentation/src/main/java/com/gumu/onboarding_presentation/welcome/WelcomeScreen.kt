package com.gumu.onboarding_presentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.core.R
import com.gumu.core_ui.navigation.Screen
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.core_ui.util.UiEvent
import com.gumu.onboarding_presentation.components.ActionButton

@Composable
fun WelcomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val spacing = LocalSpacing.current
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.spaceMedium)
        ) {
            Text(
                text = stringResource(id = R.string.welcome_text),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            ActionButton(
                text = stringResource(id = R.string.next),
                onClick = { onNavigate(UiEvent.Navigate(Screen.Gender.route)) }
            )
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    CalorieTrackerTheme {
        WelcomeScreen {}
    }
}
