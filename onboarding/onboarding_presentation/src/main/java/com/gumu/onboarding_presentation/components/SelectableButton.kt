package com.gumu.onboarding_presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing

@Composable
fun SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    color: Color,
    selectedColor: Color,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                border = BorderStroke(width = 2.dp, color = color),
                shape = RoundedCornerShape(100.dp)
            )
            .background(
                color = if (isSelected) color else Color.Transparent,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable(onClick = onClick)
            .padding(
                vertical = LocalSpacing.current.spaceMedium,
                horizontal = LocalSpacing.current.spaceLarge
            )
    ) {
        Text(
            text = text,
            color = if (isSelected) selectedColor else color
        )
    }
}

@Preview
@Composable
private fun SelectableButtonPreview() {
    CalorieTrackerTheme {
        Column {
            SelectableButton(
                text = "Option 1",
                isSelected = true,
                color = MaterialTheme.colorScheme.surfaceVariant,
                selectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            SelectableButton(
                text = "Option 2",
                isSelected = false,
                color = MaterialTheme.colorScheme.surfaceVariant,
                selectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = {}
            )
        }
    }
}
