package com.gumu.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gumu.core.R
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing
import com.gumu.tracker_domain.model.MealType
import com.gumu.tracker_domain.model.TrackedFood
import com.gumu.tracker_presentation.components.NutrientInfo
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackedFoodItem(
    food: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    ElevatedCard(
        onClick = onDeleteClick,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = spacing.spaceMedium)
                .height(100.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .crossfade(true)
                        .data(food.imgUrl)
                        .error(R.drawable.ic_burger)
                        .fallback(R.drawable.ic_burger)
                        .build()
                ),
                contentDescription = food.name,
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
                    text = food.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
                Text(
                    text = stringResource(
                        id = R.string.nutrient_info,
                        food.amount,
                        food.calories
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.delete),
                    modifier = Modifier
                        .align(Alignment.End)
                        .clip(CircleShape)
                        .clickable(onClick = onDeleteClick)
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NutrientInfo(
                        amount = food.carbs,
                        unit = stringResource(id = R.string.grams),
                        name = stringResource(id = R.string.carbs),
                        amountTextSize = 16.sp,
                        unitTextSize = 12.sp,
                        nameTextStyle = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    NutrientInfo(
                        amount = food.protein,
                        unit = stringResource(id = R.string.grams),
                        name = stringResource(id = R.string.protein),
                        amountTextSize = 16.sp,
                        unitTextSize = 12.sp,
                        nameTextStyle = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    NutrientInfo(
                        amount = food.fat,
                        unit = stringResource(id = R.string.grams),
                        name = stringResource(id = R.string.fat),
                        amountTextSize = 16.sp,
                        unitTextSize = 12.sp,
                        nameTextStyle = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TrackedFoodItemPreview() {
    CalorieTrackerTheme {
        TrackedFoodItem(
            food = TrackedFood(
                name = "Pasta",
                carbs = 0,
                protein = 0,
                fat = 0,
                imgUrl = null,
                type = MealType.Lunch,
                amount = 0,
                date = LocalDate.now(),
                calories = 0
            ),
            onDeleteClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}
