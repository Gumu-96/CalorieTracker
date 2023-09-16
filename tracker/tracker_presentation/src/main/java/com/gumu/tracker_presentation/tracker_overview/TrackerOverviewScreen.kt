package com.gumu.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.core.R
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.core_ui.util.UiEvent
import com.gumu.tracker_presentation.tracker_overview.components.AddButton
import com.gumu.tracker_presentation.tracker_overview.components.DaySelector
import com.gumu.tracker_presentation.tracker_overview.components.ExpandableMeal
import com.gumu.tracker_presentation.tracker_overview.components.NutrientsHeader
import com.gumu.tracker_presentation.tracker_overview.components.TrackedFoodItem
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
    val context = LocalContext.current

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
        items(uiState.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggle = { onEvent(TrackerOverviewEvent.ToggleMealClick(meal)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.trackedFoods.filter { it.type == meal.mealType }.forEach {
                    TrackedFoodItem(
                        food = it,
                        onDeleteClick = {
                            onEvent(TrackerOverviewEvent.DeleteTrackedFoodClick(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                }
                AddButton(
                    text = stringResource(id = R.string.add_meal, meal.name.asString(context)),
                    onClick = { onEvent(TrackerOverviewEvent.AddFoodClick(meal)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.spaceSmall)
                )
            }
        }
    }
}

@Preview(showBackground = true)
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
