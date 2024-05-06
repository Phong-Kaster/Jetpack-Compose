package com.example.jetpack.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.jetpack.R
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.LocalNavController
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor

@Composable
fun CoreFloatingMenu() {
    val navController = LocalNavController.current ?: rememberNavController()
    var expand by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        AnimatedVisibility(
            visible = expand,
            enter = slideInVertically() + fadeIn() + expandVertically(
                expandFrom = Alignment.Top
            ),
            exit = slideOutVertically() + fadeOut() + shrinkVertically(
                shrinkTowards = Alignment.Bottom
            ),
            content = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Menu.entries.forEach {
                        IconButton(
                            onClick = {
                                navController.navigate(it.destinationId)
                            },
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(color = PrimaryColor)
                                .size(50.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = it.drawableId),
                                contentDescription = stringResource(id = R.string.icon),
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        IconButton(
            onClick = {
                expand = !expand
            },
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = Color.Transparent)
                .size(50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_nazi_symbol),
                contentDescription = null,
                tint = OppositePrimaryColor,
                modifier = Modifier
                    .size(30.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.Red,
                            radius = size.width
                        )

                        drawCircle(
                            color = Color.White,
                            radius = size.width * 0.55F
                        )
                    }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCoreFloatingMenu() {
    CoreFloatingMenu()
}