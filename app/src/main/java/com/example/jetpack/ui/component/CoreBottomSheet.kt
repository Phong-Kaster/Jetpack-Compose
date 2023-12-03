package com.example.jetpack.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreBottomSheet(
    enable: Boolean,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape: Shape = RoundedCornerShape(20.dp, 20.dp),
    containerColor: Color = Color.White,
    windowInsets: WindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    if (enable) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    onDismissRequest()
                }
            },
            shape = shape,
            containerColor = containerColor,
            sheetState = sheetState,
            dragHandle = null,
            windowInsets = windowInsets,
        ) {
            Column(modifier = Modifier.navigationBarsPadding()) {
                content()
            }
        }
    }
}