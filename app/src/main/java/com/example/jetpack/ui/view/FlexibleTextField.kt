package com.example.jetpack.ui.view


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A flexible, animated text field that expands vertically as the user types,
 * supports up to 4 lines, and automatically scrolls into view when covered
 * by the on-screen keyboard (important when used in LazyColumn).
 *
 * This composable is designed for chat-input style experiences.
 *
 * Key features:
 * - Dynamic height that grows based on line count.
 * - Corner radius animation: large when single-line, smaller when multi-line.
 * - Auto-scroll when focused or when cursor moves (BringIntoViewRequester).
 * - Clear (X) button to delete all text when non-empty.
 *
 * @param value            Current text value of the input.
 * @param onValueChange    Callback when the text changes.
 * @param onDone           Callback triggered when user presses the IME "Done".
 * @param modifier         Modifier for external styling and positioning.
 */
@Composable
fun FlexibleTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onDone: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // -------------------------------------------------------------------------
    // Auto-scroll helpers
    // -------------------------------------------------------------------------
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    // Track how many lines the text currently occupies
    var lineCount by remember { mutableStateOf(1) }

    // -------------------------------------------------------------------------
    // Dynamic UI: shape + height change depending on line count
    // -------------------------------------------------------------------------

    // Corner radius: rounded when single-line, smaller when multi-line
    val targetCorner = if (lineCount <= 1) 50 else 16
    val animatedCorner by animateIntAsState(targetCorner, label = "cornerAnimation")

    // Height grows as more lines appear (maxLines = 4)
    val targetHeight = when (lineCount) {
        1 -> 44.dp                  // default single-line
        in 2..4 -> (44 + lineCount * 15).dp  // gradually grow
        else -> 104.dp              // max height
    }
    val animatedHeight by animateDpAsState(targetHeight, label = "heightAnimation")

    // Vertical alignment adjusts when switching between single-line vs multi-line
    val verticalAlignment =
        if (lineCount == 1) Alignment.CenterVertically else Alignment.Top

    // -------------------------------------------------------------------------
    // Main container (animated height + shape)
    // -------------------------------------------------------------------------
    Box(
        modifier = modifier
            .fillMaxWidth()
            // Required for auto-scrolling when keyboard appears
            .bringIntoViewRequester(bringIntoViewRequester)
            .height(animatedHeight)
            .border(
                width = 1.2.dp,
                brush = Brush.linearGradient(
                    listOf(Color(0xFF004BDC), Color(0xFF9EFFFF))
                ),
                shape = RoundedCornerShape(animatedCorner.dp)
            )
            .clip(RoundedCornerShape(animatedCorner.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {

        // ---------------------------------------------------------------------
        // The actual text field (BasicTextField)
        // ---------------------------------------------------------------------
        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange(it)

                // Auto-scroll when cursor moves or text changes
                coroutineScope.launch {
                    delay(50)  // small delay for layout to settle
                    bringIntoViewRequester.bringIntoView()
                }
            },
            textStyle = customizedTextStyle(
                fontSize = 15,
                fontWeight = 400,
                color = Color.White
            ),
            minLines = 1,
            maxLines = 4,

            // Keyboard behavior
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                onDone(value)
            },

            cursorBrush = Brush.verticalGradient(
                listOf(Color.White, Color.White)
            ),

            modifier = Modifier
                .fillMaxSize()
                // Auto-scroll when the field gains focus
                .onFocusChanged { focus ->
                    if (focus.isFocused) {
                        coroutineScope.launch {
                            delay(250)  // wait for keyboard animation
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },

            // Track line count when text layout changes
            onTextLayout = { layoutResult ->
                lineCount = layoutResult.lineCount
            },

            // Custom decoration (placeholder + clear button)
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = verticalAlignment,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {

                    // --------------------- LEFT: Placeholder + Input ---------------------
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_message),
                                style = customizedTextStyle(
                                    fontSize = 12,
                                    fontWeight = 400,
                                    color = Color.DarkGray
                                )
                            )
                        }
                        innerTextField()
                    }

                    // --------------------- RIGHT: Clear Button ---------------------
                    if (value.isNotEmpty()) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { onValueChange("") }
                        )
                    }
                }
            }
        )
    }
}




@Preview
@Composable
private fun PreviewDynamicTextField() {
    FlexibleTextField(
        value = "Enter"
    )
}