package com.example.jetpack.ui.view.symptombottomsheet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apero.lunacycle.R
import com.apero.lunacycle.domain.model.MenstruationDay
import com.apero.lunacycle.ui.theme.TextDefault
import com.apero.lunacycle.ui.theme.customizedTextStyle

@Composable
fun SymptomDateSwitcher(
    onChangeMenstruationDay: (MenstruationDay) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var  menstruationDay by remember { mutableStateOf(MenstruationDay()) }
    // Date navigation section with left/right arrows
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        // Left arrow for previous date
        Icon(
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = stringResource(R.string.backward),
            modifier = Modifier
                .clip(shape = CircleShape)
                .clickable {
                    menstruationDay = menstruationDay.copy(epochDay = menstruationDay.epochDay - 1)
                    onChangeMenstruationDay(menstruationDay)
                }
        )

        // Current date display (hardcoded - should be dynamic)
        Text(
            text = menstruationDay.getFormattedDate("dd MMM"),
            style = customizedTextStyle(
                fontSize = 14,
                fontWeight = 400,
                color = TextDefault
            ),
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        // Right arrow for next date
        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = stringResource(R.string.forward),
            modifier = Modifier
                .clip(shape = CircleShape)
                .clickable {
                    menstruationDay = menstruationDay.copy(epochDay = menstruationDay.epochDay + 1)
                    onChangeMenstruationDay(menstruationDay)
                }
        )
    }
}

@Preview
@Composable
private fun PreviewSymptomDateSwitcher() {
    SymptomDateSwitcher(
        modifier = Modifier.background(color = Color.White).padding(16.dp)
    )
}