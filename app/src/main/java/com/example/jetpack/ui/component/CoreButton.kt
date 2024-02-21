package com.example.jetpack.ui.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body14
import com.example.jetpack.util.ViewUtil

@Composable
fun CoreButton(
    onClick: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    // For text
    text: String = stringResource(id = R.string.fake_title),
    backgroundColor: Color = PrimaryColor,
    textColor: Color = OppositePrimaryColor,
    textStyle: TextStyle = body14,
    // For icon
    @DrawableRes leftIcon: Int? = null,
    leftIconColor: Color = Color.Black,
    @DrawableRes rightIcon: Int? = null,
    rightIconColor: Color = Color.Black,
    // For border
    shape: Shape = RoundedCornerShape(15.dp),
    borderStroke: BorderStroke? = null,
    // For padding & margin
    paddingHorizontal: Dp = 16.dp,
    paddingVertical: Dp = 16.dp,
    marginHorizontal: Dp = 16.dp,
    marginVertical: Dp = 16.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(shape = shape)
            .padding(horizontal = marginHorizontal, vertical = marginVertical)
            .clickable { onClick() }
            .let { if (borderStroke != null) it.border(borderStroke, shape) else it }
            .background(
                color = backgroundColor,
                shape = shape
            )
            .padding(horizontal = paddingHorizontal, vertical = paddingVertical)
    )
    {
        if (leftIcon != null) {
            Icon(
                painter = painterResource(id = leftIcon),
                tint = leftIconColor,
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
        }


        Text(
            text = text,
            style = textStyle,
            color = textColor
        )

        if (rightIcon != null) {
            Icon(
                painter = painterResource(id = rightIcon),
                tint = rightIconColor,
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
        }
    }
}

@Composable
fun SolidButton(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    // For text
    text: String = stringResource(id = R.string.fake_title),
    backgroundColor: Color = PrimaryColor,
    textColor: Color = OppositePrimaryColor,
    textStyle: TextStyle = body14,
    shape: Shape = RoundedCornerShape(15.dp),
    // For padding & margin
    paddingHorizontal: Dp = 16.dp,
    paddingVertical: Dp = 16.dp,
    marginHorizontal: Dp = 16.dp,
    marginVertical: Dp = 16.dp
) {
    CoreButton(
        onClick = onClick,
        modifier = modifier,
        text = text,
        backgroundColor = backgroundColor,
        textColor = textColor,
        textStyle = textStyle,
        shape = shape,
        marginHorizontal = marginHorizontal,
        marginVertical = marginVertical,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical
    )
}

@Composable
fun OutlineButton(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    // For text
    text: String = stringResource(id = R.string.fake_title),
    backgroundColor: Color = Color.Transparent,
    textColor: Color = OppositePrimaryColor,
    textStyle: TextStyle = body14,
    // For border
    shape: Shape = RoundedCornerShape(15.dp),
    borderStroke: BorderStroke? = BorderStroke(width = 1.dp, color = Color.Yellow),
    // For padding & margin
    paddingHorizontal: Dp = 16.dp,
    paddingVertical: Dp = 16.dp,
    marginHorizontal: Dp = 16.dp,
    marginVertical: Dp = 16.dp
) {
    CoreButton(
        onClick = onClick,
        modifier = modifier,
        text = text,
        backgroundColor = backgroundColor,
        textColor = textColor,
        textStyle = textStyle,
        shape = shape,
        borderStroke = borderStroke,
        marginHorizontal = marginHorizontal,
        marginVertical = marginVertical,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical
    )
}

@Preview
@Composable
fun PreviewCoreButton() {
    ViewUtil.PreviewContent {
        CoreButton(
            text = "Core Button",
            modifier = Modifier.width(250.dp),
            leftIcon = R.drawable.ic_back,
            leftIconColor = Color.Red
        )

        CoreButton(
            text = "Core Button",
            modifier = Modifier.width(250.dp),
            rightIcon = R.drawable.ic_forward,
            rightIconColor = Color.Blue
        )

        CoreButton(
            text = "Core Button",
            borderStroke = BorderStroke(
                width = 2.dp,
                color = Color.Red
            )
        )

        SolidButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = "Solid Button",
            textColor = Color.White,
            backgroundColor = Color.Blue
        )

        OutlineButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = "Outline Button",
            textColor = Color.Yellow
        )
    }
}