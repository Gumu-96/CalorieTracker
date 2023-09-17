package com.gumu.tracker_presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.core.R
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.core_ui.util.UiEvent
import com.gumu.tracker_domain.model.MealType
import com.gumu.tracker_presentation.search.components.SearchTextField
import com.gumu.tracker_presentation.search.components.TrackableFoodItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    onNavigateUp: () -> Unit,
    uiState: SearchState,
    mealName: String?,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    uiEvents: Flow<UiEvent>,
    onEvent: (SearchEvent) -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        uiEvents.collect { event ->
            when (event) {
                is UiEvent.NavigateUp -> onNavigateUp()
                is UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
                            withDismissAction = true
                        )
                        keyboardController?.hide()
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState)}
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(spacing.spaceMedium)
        ) {
            Text(
                text = stringResource(id = R.string.add_meal, mealName ?: ""),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            SearchTextField(
                query = uiState.query,
                onQueryChange = { onEvent(SearchEvent.QueryChange(it)) },
                onSearch = {
                    onEvent(SearchEvent.Search)
                    keyboardController?.hide()
                },
                onFocusChanged = { onEvent(SearchEvent.SearchFocusChange(it.isFocused)) },
                shouldShowHint = uiState.isHintVisible
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = uiState.trackableFoods) { foodState ->
                    TrackableFoodItem(
                        foodState = foodState,
                        onToggle = { onEvent(SearchEvent.ToggleTrackableFood(foodState.food)) },
                        onAmountChange = {
                            onEvent(SearchEvent.AmountForFoodChange(it, foodState.food))
                        },
                        onTrack = {
                            onEvent(
                                SearchEvent.TrackFoodClick(
                                    food = foodState,
                                    type = MealType.fromString(mealName ?: ""),
                                    date = LocalDate.of(year, month, dayOfMonth)
                                )
                            )
                            keyboardController?.hide()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isSearching -> CircularProgressIndicator()
                uiState.trackableFoods.isEmpty() -> {
                    Text(text = stringResource(id = R.string.no_results))
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    CalorieTrackerTheme {
        SearchScreen(
            onNavigateUp = {},
            uiState = SearchState(),
            mealName = "Breakfast",
            dayOfMonth = 1,
            month = 1,
            year = 2023,
            uiEvents = flow {},
            onEvent = {}
        )
    }
}
