package com.example.jetpack.ui.view.symptombottomsheet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apero.lunacycle.domain.enums.Symptom
import com.apero.lunacycle.domain.enums.SymptomCategory
import com.apero.lunacycle.ui.theme.customizedTextStyle


@Composable
fun SymptomCategoryLazyRow(
    chosenSymptomCategory: SymptomCategory,
    onClick: (SymptomCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()

//    LaunchedEffect(chosenSymptomCategory) {
//        val targetIndex = SymptomCategory.entries.indexOf(chosenSymptomCategory)
//        state.animateScrollToItem(targetIndex)
//    }

    Column(modifier = modifier.fillMaxWidth()){
        LazyRow(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = SymptomCategory.entries.toTypedArray(),
                itemContent = { index, symptomCategory ->
                    SymptomCategoryElement(
                        text = stringResource(symptomCategory.text),
                        enable = chosenSymptomCategory == symptomCategory,
                        onClick = { onClick(symptomCategory) },
                        modifier = Modifier
                    )
                })
        }

        // Helper text explaining symptom variations
        Text(
            text = stringResource(chosenSymptomCategory.description),
            style = customizedTextStyle(
                fontSize = 12,
                fontWeight = 400,
                color = Color(0xFF888888),
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }


}

@Preview
@Composable
private fun PreviewSymptomCategoryLazyRow() {
    SymptomCategoryLazyRow(
        chosenSymptomCategory = SymptomCategory.Medicine,
        onClick = { },
        modifier = Modifier.background(Color.White)
    )
}