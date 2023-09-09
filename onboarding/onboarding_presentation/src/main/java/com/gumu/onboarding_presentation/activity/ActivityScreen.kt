package com.gumu.onboarding_presentation.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.core.domain.model.ActivityLevel
import com.gumu.core_ui.R
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.core_ui.util.UiEvent
import com.gumu.onboarding_presentation.components.ActionButton
import com.gumu.onboarding_presentation.components.SelectableButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun ActivityScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    selectedActivity: ActivityLevel,
    uiEvents: Flow<UiEvent>,
    onEvent: (ActivityEvent) -> Unit
) {
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = Unit) {
        uiEvents.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_activity_level),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.low),
                    isSelected = selectedActivity is ActivityLevel.Low,
                    onClick = { onEvent(ActivityEvent.ActivityClick(ActivityLevel.Low)) }
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                SelectableButton(
                    text = stringResource(id = R.string.medium),
                    isSelected = selectedActivity is ActivityLevel.Medium,
                    onClick = { onEvent(ActivityEvent.ActivityClick(ActivityLevel.Medium)) }
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                SelectableButton(
                    text = stringResource(id = R.string.high),
                    isSelected = selectedActivity is ActivityLevel.High,
                    onClick = { onEvent(ActivityEvent.ActivityClick(ActivityLevel.High)) }
                )
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { onEvent(ActivityEvent.NextClick) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(spacing.spaceMedium)
        )
    }
}

@Preview
@Composable
private fun ActivityScreenPreview() {
    CalorieTrackerTheme {
        Surface {
            ActivityScreen(
                onNavigate = {},
                selectedActivity = ActivityLevel.Medium,
                uiEvents = flow { },
                onEvent = {}
            )
        }
    }
}
