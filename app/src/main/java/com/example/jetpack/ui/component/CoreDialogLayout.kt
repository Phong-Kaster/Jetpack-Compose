package com.example.jetpack.ui.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body12
import com.example.jetpack.ui.theme.body18
import com.example.jetpack.util.ViewUtil

@Composable
fun CoreDialogLayout(
    title: String = stringResource(R.string.are_you_sure_to_exit),
    content: String? = null,
    imageVector: ImageVector? = null,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Background, shape = RoundedCornerShape(15.dp))
            .padding(16.dp)
    ) {
        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


        Text(
            text = title,
            color = PrimaryColor,
            style = body18,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        if (content != null) {
            Text(
                text = stringResource(id = R.string.fake_message),
                color = PrimaryColor, style = body12,
                textAlign = TextAlign.Center
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlineButton(
                onClick = onCancel,
                text = stringResource(id = R.string.cancel),
                textColor = PrimaryColor,
                borderStroke = BorderStroke(width = 1.dp, color = PrimaryColor),
                modifier = Modifier.weight(1F),
                paddingVertical = 5.dp
            )

            SolidButton(
                onClick = onConfirm,
                text = stringResource(id = R.string.ok),
                textColor = OppositePrimaryColor,
                backgroundColor = PrimaryColor,
                modifier = Modifier.weight(1F),
                paddingVertical = 5.dp
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun CoreDialogLayout(
    title: String = stringResource(R.string.are_you_sure_to_exit),
    content: String? = null,
    @DrawableRes icon: Int? = null,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Background, shape = RoundedCornerShape(15.dp))
            .padding(16.dp)
    ) {
        if (icon != null) {
            Icon(
                painter =  painterResource(id =  icon),
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(35.dp)
            )
        }


        Text(
            text = title,
            color = PrimaryColor,
            style = body18,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        if (content != null) {
            Text(
                text = stringResource(id = R.string.fake_message),
                color = PrimaryColor, style = body12,
                textAlign = TextAlign.Center
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlineButton(
                onClick = onCancel,
                text = stringResource(id = R.string.cancel),
                textColor = PrimaryColor,
                borderStroke = BorderStroke(width = 1.dp, color = PrimaryColor),
                modifier = Modifier.weight(1F),
                paddingVertical = 5.dp
            )

            SolidButton(
                onClick = onConfirm,
                text = stringResource(id = R.string.ok),
                textColor = OppositePrimaryColor,
                backgroundColor = PrimaryColor,
                modifier = Modifier.weight(1F),
                paddingVertical = 5.dp
            )
        }
    }
}

@Preview
@Composable
fun PrevCoreDialogLayout() {
    ViewUtil.PreviewContent {
        CoreDialogLayout(
            icon = R.drawable.ic_disclaimer,
            content = stringResource(id = R.string.fake_message)
        )
    }
}