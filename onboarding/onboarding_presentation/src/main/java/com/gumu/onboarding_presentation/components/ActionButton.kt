package com.gumu.onboarding_presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.core_ui.theme.CalorieTrackerTheme

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    CalorieTrackerTheme {
        ActionButton(text = "Do Something") {}
    }
}
