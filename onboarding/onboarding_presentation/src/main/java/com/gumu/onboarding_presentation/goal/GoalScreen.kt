package com.gumu.onboarding_presentation.goal

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
import com.gumu.core.R
import com.gumu.core.domain.model.GoalType
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.core_ui.util.UiEvent
import com.gumu.onboarding_presentation.components.ActionButton
import com.gumu.onboarding_presentation.components.SelectableButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun GoalScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    selectedGoal: GoalType,
    uiEvents: Flow<UiEvent>,
    onEvent: (GoalEvent) -> Unit
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
                text = stringResource(id = R.string.lose_keep_or_gain_weight),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.lose),
                    isSelected = selectedGoal is GoalType.LoseWeight,
                    onClick = { onEvent(GoalEvent.GoalClick(GoalType.LoseWeight)) }
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                SelectableButton(
                    text = stringResource(id = R.string.keep),
                    isSelected = selectedGoal is GoalType.KeepWeight,
                    onClick = { onEvent(GoalEvent.GoalClick(GoalType.KeepWeight)) }
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                SelectableButton(
                    text = stringResource(id = R.string.gain),
                    isSelected = selectedGoal is GoalType.GainWeight,
                    onClick = { onEvent(GoalEvent.GoalClick(GoalType.GainWeight)) }
                )
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { onEvent(GoalEvent.NextClick) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(spacing.spaceMedium)
        )
    }
}

@Preview
@Composable
private fun GoalScreenPreview() {
    CalorieTrackerTheme {
        Surface {
            GoalScreen(
                onNavigate = {},
                selectedGoal = GoalType.KeepWeight,
                uiEvents = flow { },
                onEvent = {}
            )
        }
    }
}
