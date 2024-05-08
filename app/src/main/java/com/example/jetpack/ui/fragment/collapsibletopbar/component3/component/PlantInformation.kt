package com.example.jetpack.ui.fragment.collapsibletopbar.component3.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.fragment.collapsibletopbar.component3.header.ToolbarState
import com.example.jetpack.ui.fragment.collapsibletopbar.state.Dimens
import com.example.jetpack.ui.fragment.collapsibletopbar.state.visible
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun PlantInformation(
    onNamePosition: (Float) -> Unit,
    toolbarState: ToolbarState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.flag_of_nazi_germany),
            style = customizedTextStyle(),
            color = PrimaryColor,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
                .onGloballyPositioned { onNamePosition(it.positionInWindow().y) }
                .visible { toolbarState == ToolbarState.HIDDEN }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.the_flag_of_nazi_germany),
                style = customizedTextStyle(fontSize = 18, color = PrimaryColor),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.after_rejecting_many_suggestions),
                style = customizedTextStyle(fontSize = 18, color = PrimaryColor),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.after_rejecting_many_suggestions),
                style = customizedTextStyle(fontSize = 18, color = PrimaryColor),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.i_myself_meanwhile_after_innumerable),
                style = customizedTextStyle(fontSize = 18, color = PrimaryColor),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.after_hitler_was_appointed),
                style = customizedTextStyle(fontSize = 18, color = PrimaryColor),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.on_15_september_1935_one_year_after_the_death),
                style = customizedTextStyle(fontSize = 18, color = PrimaryColor),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPlantInformation() {
    PlantInformation(
        modifier = Modifier.fillMaxWidth(),
        onNamePosition = {},
        toolbarState = ToolbarState.HIDDEN
    )
}