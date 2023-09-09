package com.gumu.onboarding_presentation.age

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.core.R
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.core_ui.util.UiEvent
import com.gumu.onboarding_presentation.components.ActionButton
import com.gumu.onboarding_presentation.components.UnitTextField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    age: String,
    uiEvents: Flow<UiEvent>,
    onEvent: (AgeEvent) -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        uiEvents.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
                            withDismissAction = true
                        )
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.whats_your_age),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                UnitTextField(
                    value = age,
                    onValueChange = { onEvent(AgeEvent.AgeChange(it)) },
                    unit = stringResource(id = R.string.years)
                )
            }
            ActionButton(
                text = stringResource(id = R.string.next),
                onClick = { onEvent(AgeEvent.NextClick) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(spacing.spaceMedium)
            )
        }
    }
}

@Preview
@Composable
private fun AgeScreenPreview() {
    CalorieTrackerTheme {
        Surface {
            AgeScreen(
                onNavigate = {},
                age = "25",
                uiEvents = flow { },
                onEvent = {}
            )
        }
    }
}
