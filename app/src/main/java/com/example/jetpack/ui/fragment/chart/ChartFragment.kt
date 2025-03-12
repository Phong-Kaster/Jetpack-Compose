package com.example.jetpack.ui.fragment.chart

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.ChartShortcut
import com.example.jetpack.ui.component.CoreAlertDialog
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.component.CoreTextAnimationDialog
import com.example.jetpack.ui.dialog.WheelTimePickerDialog
import com.example.jetpack.ui.fragment.chart.component.AreaChartScreen
import com.example.jetpack.ui.fragment.chart.component.BarChartScreen
import com.example.jetpack.ui.fragment.chart.component.CalendarScreen
import com.example.jetpack.ui.fragment.chart.component.ChartTopBar
import com.example.jetpack.ui.fragment.chart.component.ColourScreen
import com.example.jetpack.ui.fragment.chart.component.ComponentScreen
import com.example.jetpack.ui.fragment.chart.component.LineChartScreen
import com.example.jetpack.ui.fragment.chart.component.RingChartScreen
import com.example.jetpack.ui.fragment.chart.component.TestScreen
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.AnalogueClock
import com.example.jetpack.ui.view.CountdownSnackbar
import com.example.jetpack.ui.view.ShimmerText
import com.example.jetpack.util.AppUtil.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * # [FloatingActionButton Lost in Jetpack Compose? A Material 3 Bottom Sheet Rescue Mission](https://medium.com/app-dev-pro-tips/floatingactionbutton-lost-in-jetpack-compose-a-material-3-bottom-sheet-rescue-mission-d77c959ef596)
 */
@AndroidEntryPoint
class InsightFragment : CoreFragment() {

    private var showAlertDialog by mutableStateOf(false)
    private var showDialog by mutableStateOf(false)
    private var showDottedTextDialog by mutableStateOf(false)
    private var showWheelTimePickerDialog by mutableStateOf(false)


    /*************************************************
     * ComposeView
     */
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        val context = LocalContext.current


        InsightLayout(
            onClick = { context.showToast(message = context.getString(R.string.app_name)) },
            onOpenAlertDialog = { showAlertDialog = true },
            onOpenDialog = { showDialog = true },
            onOpenDottedTextDialog = { showDottedTextDialog = true },
            onOpenWheelTimePicker = { showWheelTimePickerDialog = true },
        )

        HomeDialog(
            enable = showDialog,
            onDismissRequest = { showDialog = false },
        )

        CoreAlertDialog(
            enable = showAlertDialog,
            onDismissRequest = { showAlertDialog = false },
        )

        CoreTextAnimationDialog(
            enable = showDottedTextDialog,
            onDismissRequest = { showDottedTextDialog = false },
        )

        WheelTimePickerDialog(
            enable = showWheelTimePickerDialog,
            screenRecordingTime = 18960,
            onDismissRequest = { showWheelTimePickerDialog = false },
            onConfirm = { hour, minute, second ->
                showToast("$hour:$minute:$second")
            }
        )
    }
}

/**
 * # [FloatingActionButton Lost in Jetpack Compose? A Material 3 Bottom Sheet Rescue Mission](https://medium.com/app-dev-pro-tips/floatingactionbutton-lost-in-jetpack-compose-a-material-3-bottom-sheet-rescue-mission-d77c959ef596)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightLayout(
    onClick: () -> Unit = {},

    onOpenAlertDialog: () -> Unit = {},
    onOpenDottedTextDialog: () -> Unit = {},
    onOpenDialog: () -> Unit = {},
    onOpenWheelTimePicker: () -> Unit = {},

    ) {
    val context = LocalContext.current
    var chosenChip: ChartShortcut by rememberSaveable { mutableStateOf(ChartShortcut.Calendar) }

    /** For Bottom Sheet Scaffold*/
    val bottomSheetState = rememberStandardBottomSheetState()
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState)

    /** For Floating Action Button*/
    var fabHeight by remember { mutableIntStateOf(0) }

    /** For show countdown snackbar*/
    val scope = rememberCoroutineScope()
    // Define a SnackbarHostState to manage the state of the snackbar
    val snackbarHostState = remember { SnackbarHostState() }


    CoreLayout(
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Create a SnackbarHost to display the snackbar
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                ) { data ->
                    // Use the CountdownSnackbar
                    CountdownSnackbar(
                        snackbarData = data,
                        containerColor = LocalTheme.current.primary,
                        contentColor = LocalTheme.current.secondary,
                    )
                }

                CoreBottomBar()
            }
        },
        backgroundColor = LocalTheme.current.background
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ChartTopBar(
                    chosenChip = chosenChip,
                    onChange = { chosenChip = it }
                )
            },
            sheetPeekHeight = 50.dp,
            containerColor = LocalTheme.current.background,
            sheetContainerColor = LocalTheme.current.background.copy(
                red = 0.05f,
                green = 0.15f,
                blue = 0.3f,
            ),
            modifier = Modifier
                .fillMaxSize()
                .background(color = LocalTheme.current.background),
            sheetContent = {
                ShimmerText(
                    text = "Phong Kaster",
                    shimmerColor = LocalTheme.current.primary,
                    textStyle = customizedTextStyle(
                        fontSize = 32,
                        fontWeight = 600,
                    ),
                    textAlign = TextAlign.Center,
                    animationSpec = tween(
                        durationMillis = 3000,
                        delayMillis = 0,
                        easing = LinearEasing
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    AnimatedContent(
                        targetState = chosenChip,
                        label = stringResource(id = R.string.fake_title),
                        content = { chosenChip ->
                            when (chosenChip) {
                                ChartShortcut.AnalogueClock -> AnalogueClock()
                                ChartShortcut.LineChart -> LineChartScreen()
                                ChartShortcut.RingChart -> RingChartScreen()
                                //ChartShortcut.BubbleChart -> BubbleChartScreen()
                                ChartShortcut.BarChart -> BarChartScreen()
                                ChartShortcut.AreaChart -> AreaChartScreen()
                                ChartShortcut.ColourScreen -> ColourScreen()
                                ChartShortcut.Calendar -> CalendarScreen()
                                ChartShortcut.Component -> ComponentScreen(
                                    onOpenWheelTimePicker = onOpenWheelTimePicker,
                                    onOpenAlertDialog = onOpenAlertDialog,
                                    onOpenDialog = onOpenDialog,
                                    onOpenDottedTextDialog = onOpenDottedTextDialog,
                                    onOpenCountdownSnackbar = {
                                        scope.launch {
                                            // Show a snackbar
                                            val result = snackbarHostState.showSnackbar(
                                                message = "User account deleted.",
                                                actionLabel = "UNDO",
                                                duration = SnackbarDuration.Indefinite
                                            )
                                            // Handle the snackbar result
                                            when (result) {
                                                SnackbarResult.Dismissed -> context.showToast(
                                                    message = "Deleted permanently"
                                                )

                                                SnackbarResult.ActionPerformed -> context.showToast(
                                                    message = "Deletion canceled"
                                                )
                                            }
                                        }
                                    },
                                )
                                ChartShortcut.TestScreen -> TestScreen()
                                else -> Unit
                            }
                        }
                    )


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onSizeChanged(onSizeChanged = { fabHeight = it.height })
                            .offset(offset = {
                                IntOffset(
                                    x = 0,
                                    y = bottomSheetState
                                        .requireOffset()
                                        .roundToInt() - (fabHeight * 3f).roundToInt()
                                )
                            })
                            .padding(16.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onClick() }
                                .height(50.dp),
                            content = {
                                Column(modifier = Modifier.matchParentSize()) {
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .background(color = Color.Black)
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .background(color = Color(0xFFDD0000))
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .background(color = Color(0xFFFFCC00))
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_bundeswehr),
                                        contentDescription = "Icon",
                                        modifier = Modifier.size(24.dp)
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))


                                    Text(
                                        text = stringResource(id = R.string.app_name),
                                        style = customizedTextStyle(
                                            fontSize = 16,
                                            fontWeight = 600,
                                            color = Color.White
                                        )
                                    )
                                }

                            }
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewInsightLayout() {
    InsightLayout()
}