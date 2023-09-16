package com.gumu.tracker_presentation.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gumu.core.R
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.tracker_domain.model.TrackableFood
import com.gumu.tracker_presentation.components.NutrientInfo
import com.gumu.tracker_presentation.search.TrackableFoodState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackableFoodItem(
    foodState: TrackableFoodState,
    onToggle: () -> Unit,
    onAmountChange: (String) -> Unit,
    onTrack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    ElevatedCard(
        onClick = onToggle,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = spacing.spaceMedium)
                .height(100.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .crossfade(true)
                        .data(foodState.food.imageUrl)
                        .error(R.drawable.ic_burger)
                        .fallback(R.drawable.ic_burger)
                        .build()
                ),
                contentDescription = foodState.food.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = foodState.food.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
                Text(
                    text = stringResource(
                        id = R.string.kcal_per_100g,
                        foodState.food.caloriesPer100g
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            NutrientInfo(
                amount = foodState.food.carbsPer100g,
                unit = stringResource(id = R.string.grams),
                name = stringResource(id = R.string.carbs),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
                nameTextStyle = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            NutrientInfo(
                amount = foodState.food.proteinPer100g,
                unit = stringResource(id = R.string.grams),
                name = stringResource(id = R.string.protein),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
                nameTextStyle = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            NutrientInfo(
                amount = foodState.food.fatPer100g,
                unit = stringResource(id = R.string.grams),
                name = stringResource(id = R.string.fat),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
                nameTextStyle = MaterialTheme.typography.bodySmall
            )
        }
        AnimatedVisibility(visible = foodState.isExpanded) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceSmall)
            ) {
                Row(modifier = Modifier.padding(spacing.spaceSmall)) {
                    BasicTextField(
                        value = foodState.amount,
                        onValueChange = onAmountChange,
                        keyboardOptions = KeyboardOptions(
                            imeAction = if (foodState.amount.isNotBlank()) {
                                ImeAction.Done
                            } else ImeAction.Default,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onTrack()
                                defaultKeyboardAction(ImeAction.Done)
                            }
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .border(
                                shape = RoundedCornerShape(5.dp),
                                width = 1.dp,
                                color = Color.Gray
                            )
                            .alignBy(LastBaseline)
                            .padding(spacing.spaceSmall)
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                    Text(
                        text = stringResource(id = R.string.grams),
                        modifier = Modifier.alignBy(LastBaseline)
                    )
                }
                IconButton(
                    onClick = onTrack,
                    enabled = foodState.amount.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.track),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TrackableFoodItemPreview() {
    CalorieTrackerTheme {
        TrackableFoodItem(
            foodState = TrackableFoodState(
                food = TrackableFood(
                    name = "Apple",
                    imageUrl = null,
                    caloriesPer100g = 1,
                    carbsPer100g = 1,
                    proteinPer100g = 1,
                    fatPer100g = 1
                ),
                isExpanded = true
            ),
            onToggle = {},
            onAmountChange = {},
            onTrack = {}
        )
    }
}
