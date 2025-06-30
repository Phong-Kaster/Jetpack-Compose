package com.example.jetpack.ui.view.symptombottomsheet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apero.lunacycle.domain.enums.Symptom
import com.apero.lunacycle.domain.enums.SymptomCategory
import com.apero.lunacycle.ui.component.SymptomElement
import com.apero.lunacycle.util.AppUtil.figmaHeightScale

@Composable
fun SymptomHorizontalPager(
    pagerState: PagerState,
    pageToCategory: List<Pair<SymptomCategory, Int>>,
    symptomsPerPage: Int,
    selectedSymptoms: List<Symptom>,
    onSymptomToggle: (Symptom) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    // Horizontal pager for displaying symptoms in paginated format
    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 2, // Pre-load one page on each side for smooth scrolling
        modifier = modifier
            .fillMaxWidth()

    ) { globalPageIndex ->
        // Get the category and local page index for this global page
        if (globalPageIndex < pageToCategory.size) {
            val (category, localPageIndex) = pageToCategory[globalPageIndex]

            // Each page displays a subset of symptoms from the mapped category
            SymptomPage(
                symptomCategory = category,
                pageIndex = localPageIndex,
                symptomsPerPage = symptomsPerPage,
                selectedSymptoms = selectedSymptoms,
                onSymptomToggle = { symptom ->
                    onSymptomToggle(symptom)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SymptomPage(
    symptomCategory: SymptomCategory = SymptomCategory.Mood,
    pageIndex: Int = 0,
    symptomsPerPage: Int = 5,
    selectedSymptoms: List<Symptom> = emptyList(),
    onSymptomToggle: (Symptom) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Calculate symptoms for current page
    val startIndex = pageIndex * symptomsPerPage
    val endIndex = (startIndex + symptomsPerPage).coerceAtMost(symptomCategory.listOfSymptom.size)
    val currentPageSymptoms = if (startIndex < symptomCategory.listOfSymptom.size) {
        symptomCategory.listOfSymptom.subList(startIndex, endIndex)
    } else {
        emptyList()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier.fillMaxWidth(),
    ) {
        ContextualFlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp * figmaHeightScale)
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxLines = 2,
            overflow =
                ContextualFlowRowOverflow.expandOrCollapseIndicator(
                    expandIndicator = { },
                    collapseIndicator = { },
                ),
            itemCount = currentPageSymptoms.size,
            content = { index ->
                if (index < currentPageSymptoms.size) {
                    val symptom = currentPageSymptoms[index]
                    SymptomElement(
                        enable = selectedSymptoms.contains(symptom),
                        image = symptom.photo,
                        text = stringResource(symptom.text),
                        onClick = { onSymptomToggle(symptom) },
                        modifier = Modifier.wrapContentSize(),
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun PreviewSymptomHorizontalPager() {
    SymptomHorizontalPager(
        pagerState = rememberPagerState(initialPage = 0, pageCount = { 10 }),
        pageToCategory = listOf(SymptomCategory.Medicine to 0, SymptomCategory.Medicine to 1),
        symptomsPerPage = 10,
        selectedSymptoms = emptyList(),
        onSymptomToggle = {

        },
        modifier = Modifier.background(color = Color.White)
    )
}