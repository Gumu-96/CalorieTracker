package com.gumu.onboarding_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gumu.core_ui.theme.CalorieTrackerTheme
import com.gumu.core_ui.theme.LocalSpacing

@Composable
fun UnitTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.surfaceVariant,
        fontSize = 70.sp
    )
) {
    val spacing = LocalSpacing.current
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .alignBy(LastBaseline)
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Text(
            text = unit,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun UnitTextFieldPreview() {
    CalorieTrackerTheme {
        Surface {
            UnitTextField(
                value = "1000",
                onValueChange = {},
                unit = "ml"
            )
        }
    }
}
