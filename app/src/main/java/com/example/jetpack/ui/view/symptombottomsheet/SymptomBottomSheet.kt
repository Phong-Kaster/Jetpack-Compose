package com.example.jetpack.ui.view.symptombottomsheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apero.lunacycle.R
import com.apero.lunacycle.domain.enums.Symptom
import com.apero.lunacycle.domain.enums.SymptomCategory
import com.apero.lunacycle.domain.model.SymptomDiary
import com.apero.lunacycle.ui.component.CoreBottomSheet
import com.apero.lunacycle.ui.component.CoreButton2
import com.apero.lunacycle.ui.theme.Gray3
import com.apero.lunacycle.ui.theme.TextDefault
import com.apero.lunacycle.ui.theme.customizedTextStyle
import com.apero.lunacycle.ui.view.symptombottomsheet.component.SymptomCategoryElement
import com.apero.lunacycle.ui.view.symptombottomsheet.component.SymptomCategoryLazyRow
import com.apero.lunacycle.ui.view.symptombottomsheet.component.SymptomDateSwitcher
import com.apero.lunacycle.ui.view.symptombottomsheet.component.SymptomHorizontalPager
import com.apero.lunacycle.ui.view.symptombottomsheet.component.SymptomInputTemperature
import com.apero.lunacycle.ui.view.symptombottomsheet.component.SymptomInputWeight
import com.apero.lunacycle.util.AppUtil.figmaHeightScale
import kotlin.math.ceil


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomBottomSheet(
    enable: Boolean,
    onDismissRequest: () -> Unit = {},
    onConfirm: (SymptomDiary) -> Unit = {}
) {
    CoreBottomSheet(
        enable = enable,
        onDismissRequest = onDismissRequest,
        content = {
            SymptomBottomSheetContent(
                onDismissRequest = onDismissRequest,
                onConfirm = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        },
    )
}

/**
 * Displays the main content of the Symptom Bottom Sheet.
 *
 * This composable creates a comprehensive UI for symptom logging with the following features:
 * - A drag handle at the top for visual indication of the bottom sheet
 * - A header section with title and date navigation
 * - A horizontal scrollable list of symptom categories (Period, Mood, etc.)
 * - A paginated view of symptoms for the selected category
 * - A submit button to log the selected symptoms
 *
 * ## State Management:
 * - `chosenSymptomCategory`: Tracks the currently selected symptom category
 * - `listOfSymptom`: Maintains a list of selected symptoms (currently mutable list)
 * - `totalPages`: Dynamically calculated based on symptoms per page and total symptoms
 * - `pageState`: Manages the horizontal pager state for symptom pages
 *
 * ## UI Structure:
 * 1. **Drag Handle**: Gray rounded rectangle at the top
 * 2. **Header Row**: "I'm feeling" title with date navigation arrows
 * 3. **Category Selection**: Horizontal scrollable row of category buttons
 * 4. **Helper Text**: Descriptive text about symptom variations
 * 5. **Symptom Pager**: Horizontal pager displaying symptoms in pages
 * 6. **Submit Button**: Button to log selected symptoms
 *
 * ## Key Components Used:
 * - `LazyRow`: For horizontal scrolling category selection
 * - `HorizontalPager`: For paginated symptom display
 * - `SymptomCategoryElement`: Custom component for category buttons
 * - `SymptomPage`: Custom component for displaying symptoms in a page
 * - `CoreButton2`: Custom styled button for the submit action
 *
 * ## Pagination Logic:
 * - Each page displays up to 5 symptoms (`symptomsPerPage`)
 * - Total pages are calculated using ceiling division
 * - `beyondViewportPageCount = 1` for smooth scrolling performance
 *
 * @param modifier Modifier for styling and layout customization
 * @param onConfirm Callback function invoked when user taps the log button.
 *                     Receives a list of selected symptoms as parameter.
 *                     Currently passes an empty mutable list - should be updated
 *                     to track actual user selections.
 *
 * @see SymptomPage For the individual page content display
 * @see SymptomCategoryElement For the category selection buttons
 * @see CoreButton2 For the submit button styling
 */
@Composable
fun SymptomBottomSheetContent(
    onDismissRequest: () -> Unit = {},
    onConfirm: (SymptomDiary) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    /**
     * @property chosenSymptomCategory is State for tracking the currently selected symptom category
     * @property listOfSymptom is to properly track user selections and trigger recomposition when symptoms are added/removed*/
    var chosenSymptomCategory by remember { mutableStateOf(SymptomCategory.Period) }
    val listOfSymptom = remember { mutableStateListOf<Symptom>() }

    var symptomDiary by remember { mutableStateOf(SymptomDiary()) }

    /**
     * Pagination
     *
     * @property symtomsPerPage = 5 is the number of symptoms that will be displayed per page
     * @property categoryPageCounts is a list of integers that represents the number of pages for each category.
     * For instance, List(3, 6, 2) means that the first category has 3 pages, the second category has 6 pages, and the third category has 2 pages.
     * @property totalPagesAcrossAllCategories is the total number of pages across all categories
     * */
    val symptomsPerPage = 5
    val categoryPageCounts: List<Int> = remember {
        SymptomCategory.entries.map { category ->
            ceil(category.listOfSymptom.size.toDouble() / symptomsPerPage).toInt()
                .coerceAtLeast(1)
        }
    }
    val totalPagesAcrossAllCategories = categoryPageCounts.sum()

    /**
     * Mapping
     * @property pageToCategory is a list of pairs that maps a global page index to a category and a local page index
     * For example <Mood, 1>, <Mood, 2>, <Period, 1>, <Period, 2>, is a mapping of global page indices to categories and local page indices
     * @property categoryToFirstPage is a map that maps a category to its first global page index
     */
    val pageToCategory = remember {
        val mapping = mutableListOf<Pair<SymptomCategory, Int>>()
        SymptomCategory.entries.forEachIndexed { index, category ->
            val pagesForCategory = categoryPageCounts[index]
            repeat(pagesForCategory) { localPageIndex ->
                mapping.add(category to localPageIndex)
            }
        }
        mapping
    }


    /**
     * Mapping for category to first global page index
     *
     * @property categoryToFirstPage is a map that maps each symptom category to its first global page index.
     * This is used to quickly jump to the first page of a specific category when the user selects it.
     *
     * Example:
     * If we have categories with page counts: [Period: 2 pages, Mood: 3 pages, Sleep: 1 page]
     * The mapping would be:
     * - Period -> 0 (starts at global page 0)
     * - Mood -> 2 (starts at global page 2, after Period's 2 pages)
     * - Sleep -> 5 (starts at global page 5, after Period's 2 + Mood's 3 pages)
     *
     * This allows direct navigation: when user clicks "Mood" category, we can immediately
     * scroll to global page 2 using categoryToFirstPage[Mood] = 2
     */
    val categoryToFirstPage = remember {
        val mapping = mutableMapOf<SymptomCategory, Int>()
        var globalPageIndex = 0
        SymptomCategory.entries.forEachIndexed { index, category ->
            mapping[category] = globalPageIndex
            globalPageIndex += categoryPageCounts[index]
        }
        mapping
    }

    // Pager state for managing horizontal symptom page navigation across all categories
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { totalPagesAcrossAllCategories },
    )

    // Track if category change is from user click (to prevent infinite loop)
    var isUpdatingFromPager by remember { mutableStateOf(false) }

    // Update chosen category based on current page
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage < pageToCategory.size) {
            val (category, _) = pageToCategory[pagerState.currentPage]
            isUpdatingFromPager = true
            chosenSymptomCategory = category
            isUpdatingFromPager = false
        }
    }

    // Scroll pager when category is selected by user
    LaunchedEffect(chosenSymptomCategory) {
        if (!isUpdatingFromPager) {
            val targetPage = categoryToFirstPage[chosenSymptomCategory] ?: 0
            pagerState.scrollToPage(targetPage)
        }
    }

    // Main content column with centered alignment
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        // Drag handle - visual indicator for bottom sheet interaction
        Spacer(
            modifier = Modifier
                .padding(top = 9.dp, bottom = 21.dp)
                .clip(RoundedCornerShape(100.dp))
                .width(60.dp)
                .height(6.dp)
                .background(Gray3)
        )

        // Header row containing title and date navigation
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Main title text
            Text(
                text = stringResource(R.string.Im_feeling),
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 500,
                    color = TextDefault
                ),
                modifier = Modifier
            )

            // Date navigation section with left/right arrows
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                // Left arrow for previous date
//                Icon(
//                    painter = painterResource(R.drawable.ic_arrow_left),
//                    contentDescription = null,
//                )
//
//                // Current date display (hardcoded - should be dynamic)
//                Text(
//                    text = "22 January",
//                    style = customizedTextStyle(
//                        fontSize = 14,
//                        fontWeight = 400,
//                        color = TextDefault
//                    ),
//                    modifier = Modifier.padding(horizontal = 15.dp)
//                )
//
//                // Right arrow for next date
//                Icon(
//                    painter = painterResource(R.drawable.ic_arrow_right),
//                    contentDescription = null
//                )
//            }
            SymptomDateSwitcher(
                onChangeMenstruationDay = {
                    val menstruationDay = it
                    symptomDiary = symptomDiary.copy(epochDay = menstruationDay.epochDay)
                },
                modifier = Modifier.wrapContentSize()
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Horizontal scrollable row of symptom categories
        SymptomCategoryLazyRow(
            chosenSymptomCategory = chosenSymptomCategory,
            onClick = { chosenSymptomCategory = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )


        AnimatedContent(
            targetState = chosenSymptomCategory,
            modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
            content = { targetState: SymptomCategory ->
                when (targetState) {
                    SymptomCategory.Weight -> {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            SymptomInputWeight(
                                onTextChanged = { weight -> symptomDiary = symptomDiary.copy(weight = weight) },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Spacer(modifier = height(60.dp * figmaHeightScale))
                        }

                    }

                    SymptomCategory.Temperature -> {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            SymptomInputTemperature(
                                onTextChanged = { bodyTemperature -> symptomDiary = symptomDiary.copy(bodyTemperature = bodyTemperature) },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Spacer(modifier = height(60.dp * figmaHeightScale))
                        }
                    }
                    else -> {
                        // Horizontal pager for displaying symptoms in paginated format
                        SymptomHorizontalPager(
                            pagerState = pagerState,
                            pageToCategory = pageToCategory,
                            symptomsPerPage = symptomsPerPage,
                            selectedSymptoms = listOfSymptom,
                            onSymptomToggle = { symptom ->
                                if (listOfSymptom.contains(symptom)) {
                                    listOfSymptom.remove(symptom)
                                } else {
                                    listOfSymptom.add(symptom)
                                }
                                symptomDiary = symptomDiary.copy(listOfSymptoms = listOfSymptom)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            })


        // Submit button for logging selected symptoms
        CoreButton2(
            title = stringResource(R.string.log_symptoms),
            onClick = {
                onDismissRequest()
                onConfirm(symptomDiary)
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Preview
@Composable
private fun SymptomBottomSheetContentPreview() {
    SymptomBottomSheetContent(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
    )
}