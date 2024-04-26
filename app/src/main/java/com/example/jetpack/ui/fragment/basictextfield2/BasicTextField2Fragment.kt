package com.example.jetpack.ui.fragment.basictextfield2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.allCaps
import androidx.compose.foundation.text.input.maxLengthInChars
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.util.inputtransformation.DigitsOnlyTransformation
import com.example.jetpack.util.inputtransformation.VerificationCodeTransformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * this class use Basic Text Field 2
 *  A TextField of Dreams(Part 1) - https://proandroiddev.com/basictextfield2-a-textfield-of-dreams-1-2-0103fd7cc0ec
 *  A TextField of Dreams(Part 2) - https://proandroiddev.com/basictextfield2-a-textfield-of-dreams-2-2-fdc7fbbf9ffb
 */
@AndroidEntryPoint
class BasicTextFieldFragment : CoreFragment() {
    private val viewModel: BasicTextField2ViewModel by viewModels()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        BasicTextFieldLayout(
            usernameState = viewModel.username,
            isUsernameValue = viewModel.isUsernameValid.collectAsState().value,
            onBack = { safeNavigateUp() }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasicTextFieldLayout(
    usernameState: TextFieldState,
    isUsernameValue: Boolean,
    onBack: () -> Unit = {}
) {
    val digitsOnly = rememberTextFieldState()
    val maxSixCharacters = rememberTextFieldState()
    val visualTransformation = rememberTextFieldState()

    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.basic_text_field_2),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Background)
                    .padding(16.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
            ) {
                // common usage
                Text(
                    text = stringResource(R.string.common_usage),
                    style = customizedTextStyle(
                        fontSize = 18, fontWeight = 700, color = Color.Cyan
                    ), modifier = Modifier.padding(vertical = 10.dp)
                )

                BasicTextField(
                    state = usernameState,
                    cursorBrush = SolidColor(Color.Cyan),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle = customizedTextStyle(color = PrimaryColor),
                    decorator = { innerTextField ->
                        if (usernameState.text.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.search),
                                style = customizedTextStyle()
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp, color = PrimaryColor, shape = RoundedCornerShape(5.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                )
                AnimatedVisibility(
                    visible = isUsernameValue, modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Username is invalid, please try again !",
                        style = customizedTextStyle(color = Color.Red)
                    )
                }


                // Combine with Input Transformation
                Text(
                    text = "Combine with Input Transformation",
                    style = customizedTextStyle(
                        fontSize = 18, fontWeight = 700, color = Color.Cyan
                    ), modifier = Modifier.padding(vertical = 10.dp)
                )
                BasicTextField(
                    state = digitsOnly,
                    cursorBrush = SolidColor(Color.Cyan),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle = customizedTextStyle(color = PrimaryColor),
                    inputTransformation = DigitsOnlyTransformation,
                    decorator = { innerTextField ->
                        if (digitsOnly.text.isEmpty()) {
                            Text(
                                text = "Digit only", style = customizedTextStyle()
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp, color = PrimaryColor, shape = RoundedCornerShape(5.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                )

                // Combine with built-in Input Transformation
                Text(
                    text = "Combine with built-in Input Transformation",
                    style = customizedTextStyle(
                        fontSize = 18, fontWeight = 700, color = Color.Cyan
                    ), modifier = Modifier.padding(vertical = 10.dp)
                )
                BasicTextField(
                    state = maxSixCharacters,
                    cursorBrush = SolidColor(Color.Cyan),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle = customizedTextStyle(color = PrimaryColor),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    inputTransformation = InputTransformation.maxLengthInChars(6)
                        .then(InputTransformation.allCaps(Locale.current)),
                    decorator = { innerTextField ->
                        if (maxSixCharacters.text.isEmpty()) {
                            Text(
                                text = "All capitalized 6 characters", style = customizedTextStyle()
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp, color = PrimaryColor, shape = RoundedCornerShape(5.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                )

                // Visual Transformation
                Text(
                    text = "Visual transformation | OutputTransformation",
                    style = customizedTextStyle(
                        fontSize = 18,
                        fontWeight = 700,
                        color = Color.Cyan
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                BasicTextField(
                    state = visualTransformation,
                    cursorBrush = SolidColor(Color.Cyan),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle = customizedTextStyle(color = PrimaryColor, fontSize = 14),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    inputTransformation = InputTransformation.maxLengthInChars(6),
                    outputTransformation = VerificationCodeTransformation,
                    decorator = { innerTextField -> innerTextField() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = PrimaryColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                )
            }

        })
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewBasicTextField() {
    BasicTextFieldLayout(
        usernameState = TextFieldState(),
        isUsernameValue = true,
        onBack = {}
    )
}