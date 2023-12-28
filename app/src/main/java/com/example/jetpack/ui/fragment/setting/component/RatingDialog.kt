package com.example.jetpack.ui.fragment.setting.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.data.enums.Star
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.component.OutlineButton
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.Border
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.TextColor2
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.RatingBar

@Composable
fun RateDialog(
    enable: Boolean,
    onDismissRequest: () -> Unit = {},
    onSubmit: (Star, String) -> Unit = { star: Star, content: String -> },
    onClose: () -> Unit = {}
) {
    CoreDialog(
        enable = enable,
        onDismissRequest = onDismissRequest,
        content = {
            RateDialogLayout(
                onClose = onClose,
                onSubmit = { star: Star, content: String ->
                    onDismissRequest()
                    onSubmit(star, content)
                },
            )
        }
    )
}

@Composable
fun RateDialogLayout(
    onSubmit: (Star, String) -> Unit = { star: Star, content: String -> },
    onClose: () -> Unit = {}
) {
    var chosenStar by remember { mutableStateOf(Star.FIVE) }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .background(color = Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // How’s your experience so far?
        Text(
            text = stringResource(R.string.how_s_your_experience_so_far),
            color = PrimaryColor,
            style = customizedTextStyle(fontSize = 22, fontWeight = 700),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))

        // We’d love to know!
        Text(
            text = stringResource(R.string.we_d_love_to_know),
            color = TextColor2,
            style = customizedTextStyle(fontSize = 16, fontWeight = 400),
            textAlign = TextAlign.Center
        )


        // RATING BAR
        RatingBar(
            chosenStar = chosenStar,
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .background(color = Background),
            onChangeStar = { chosenStar = it }
        )

        // INPUT PLACEHOLDER
        BasicTextField(
            value = content,
            onValueChange = { newText ->
                content = newText
            },
            minLines = 3,
            cursorBrush = Brush.verticalGradient(
                colors = listOf(
                    PrimaryColor,
                    PrimaryColor,
                )
            ),
            textStyle = customizedTextStyle(fontWeight = 400, fontSize = 14),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .padding(vertical = 6.dp, horizontal = 10.dp)
                ) {
                    if (content.isBlank()) {
                        Text(
                            text = stringResource(R.string.hint_feedback),
                            style = customizedTextStyle(fontWeight = 400, fontSize = 14),
                            color = Border,
                            minLines = 3,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .height(80.dp)
        )

        //  SUBMIT BUTTON & DISMISS BUTTON
        SolidButton(
            text = stringResource(R.string.submit),
            onClick = { onSubmit(chosenStar, content) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlineButton(
            text = stringResource(id = R.string.not_now),
            textColor = PrimaryColor,
            onClick = { onClose() },
            borderStroke = BorderStroke(width = 0.dp, color = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Preview
@Composable
fun PreviewRateDialogLayout() {
    RateDialogLayout()
}