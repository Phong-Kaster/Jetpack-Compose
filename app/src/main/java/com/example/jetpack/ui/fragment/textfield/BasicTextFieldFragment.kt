package com.example.jetpack.ui.fragment.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.component.CoreTopBar3
import com.example.jetpack.ui.fragment.textfield.component.AutoFill
import com.example.jetpack.ui.fragment.textfield.component.AutoSize
import com.example.jetpack.ui.fragment.textfield.component.CombineWithBuiltInInputTransformation
import com.example.jetpack.ui.fragment.textfield.component.CombineWithInputTransformation
import com.example.jetpack.ui.fragment.textfield.component.CommonUsage
import com.example.jetpack.ui.fragment.textfield.component.SampleFlexibleTextField

import com.example.jetpack.ui.fragment.textfield.component.VisualTransformation
import com.example.jetpack.ui.theme.SofiaFontFamily
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.util.inputtransformation.VerificationCodeTransformation
import dagger.hilt.android.AndroidEntryPoint

/**
 * # this class use Basic Text Field 2
 *
 * [A TextField of Dreams(Part 1)](https://proandroiddev.com/basictextfield2-a-textfield-of-dreams-1-2-0103fd7cc0ec)
 *
 * [A TextField of Dreams(Part 2)](https://proandroiddev.com/basictextfield2-a-textfield-of-dreams-2-2-fdc7fbbf9ffb)
 *
 * [Creating Unique TextFields in Android with Jetpack Compose and DecorationBox](https://medium.com/@hhhhghhghu/creating-unique-textfields-in-android-with-jetpack-compose-and-decorationbox-9fa2a086ee73)
 */
@AndroidEntryPoint
class BasicTextFieldFragment : CoreFragment() {

    /*************************************************
     * ComposeView
     */
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        BasicTextFieldLayout(
            onBack = { safeNavigateUp() }
        )
    }
}

@Composable
fun BasicTextFieldLayout(
    onBack: () -> Unit = {}
) {
    CoreLayout(
        topBar = {
            CoreTopBar3(
                title = stringResource(R.string.basic_text_field_with_state),
                onLeftClick = onBack,
                textColor = Color.White,
                iconColor = LocalTheme.current.primary,
                modifier = Modifier.background(color = LocalTheme.current.background)
            )
        },
        modifier = Modifier.background(color = LocalTheme.current.background),
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LocalTheme.current.background)
                    .padding(16.dp)
                    .navigationBarsPadding()
            ) {
                item(key = "CommonUsage") { CommonUsage(modifier = Modifier) }

                item(key = "CombineWithBuiltInInputTransformation") { CombineWithBuiltInInputTransformation() }

                item(key = "CombineWithInputTransformation") { CombineWithInputTransformation() }

                item(key = "VisualTransformation") { VisualTransformation() }

                item(key = "Autofill") { AutoFill() }

                item(key = "AutoTextSize") { AutoSize() }

                item(key = "SampleFlexibleTextField") { SampleFlexibleTextField() }

                item(key = "footer") {
                    Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp))
                }
            }
        })
}

@Preview
@Composable
private fun PreviewBasicTextField() {
    BasicTextFieldLayout(
        onBack = {}
    )
}