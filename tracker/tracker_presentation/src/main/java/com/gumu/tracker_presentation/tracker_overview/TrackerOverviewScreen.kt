package com.gumu.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.core_ui.util.UiEvent
import com.gumu.tracker_presentation.tracker_overview.components.DaySelector
import com.gumu.tracker_presentation.tracker_overview.components.NutrientsHeader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    uiState: TrackerOverviewState,
    uiEvents: Flow<UiEvent>,
    onEvent: (TrackerOverviewEvent) -> Unit
) {
    val spacing = LocalSpacing.current

    LaunchedEffect(Unit) {
        uiEvents.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientsHeader(state = uiState)
            DaySelector(
                date = uiState.date,
                onPreviousDayClick = { onEvent(TrackerOverviewEvent.PreviousDayClick) },
                onNextDayClick = { onEvent(TrackerOverviewEvent.NextDayClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceSmall)
            )
        }
    }
}

@Preview
@Composable
private fun TrackerOverviewScreenPreview() {
    CalorieTrackerTheme {
        TrackerOverviewScreen(
            onNavigate = {},
            uiState = TrackerOverviewState(),
            uiEvents = flow { },
            onEvent = {}
        )
    }
}
