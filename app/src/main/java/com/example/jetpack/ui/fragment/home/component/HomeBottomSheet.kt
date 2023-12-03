package com.example.jetpack.ui.fragment.home.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.component.CoreBottomSheet
import com.example.jetpack.ui.component.OutlineButton
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body14
import com.example.jetpack.ui.theme.body18
import com.example.jetpack.util.ViewUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    enable: Boolean,
    onDismissRequest: () -> Unit = {},
    openLanguage: () -> Unit = {},
    openDisclaimer: () -> Unit = {},
) {
    CoreBottomSheet(
        enable = enable,
        containerColor = Background,
        onDismissRequest = onDismissRequest,
        content = {
            HomeBottomSheetLayout(
                openLanguage = openLanguage,
                openDisclaimer = openDisclaimer
            )
        })
}

@Composable
fun HomeBottomSheetLayout(
    openLanguage: () -> Unit = {},
    openDisclaimer: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = PrimaryColor,
            style = body18,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_language),
                    contentDescription = null,
                    tint = PrimaryColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.language),
                    style = body14,
                    color = PrimaryColor
                )
            }


            
            OutlineButton(
                onClick = openLanguage,
                modifier = Modifier.background(color = Color.Transparent),
                backgroundColor = Color.Transparent,
                textColor = PrimaryColor,
                shape = RoundedCornerShape(5.dp),
                borderStroke = BorderStroke(width = 1.dp, color = PrimaryColor),
                paddingVertical = 5.dp,
                paddingHorizontal = 10.dp,
                marginVertical = 0.dp,
                marginHorizontal = 0.dp
            )
        }

        Divider(
            thickness = 1.dp,
            color = PrimaryColor,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_disclaimer),
                    contentDescription = null,
                    tint = PrimaryColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.disclaimer),
                    style = body14,
                    color = PrimaryColor
                )
            }


            OutlineButton(
                onClick = openDisclaimer,
                modifier = Modifier.background(color = Color.Transparent),
                backgroundColor = Color.Transparent,
                textColor = PrimaryColor,
                shape = RoundedCornerShape(5.dp),
                borderStroke = BorderStroke(width = 1.dp, color = PrimaryColor),
                paddingVertical = 5.dp,
                paddingHorizontal = 10.dp,
                marginVertical = 0.dp,
                marginHorizontal = 0.dp
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeBottomSheetLayout() {
    ViewUtil.PreviewContent {
        HomeBottomSheetLayout()
    }
}