package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.ColorTextPrimary
import com.example.jetpack.ui.theme.customizedTextStyle

/**
 * Setting row with a switch (e.g. for 24-hour format). No navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItemWithDropdown(
    nameRes: Int,
    iconRes: Int,
    use24Hour: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    // Row must be inside ExposedDropdownMenuBox with menuAnchor(), so the dropdown is the only
    // overlay and the box measures as the Row. Otherwise the box is a separate sibling in the
    // parent Column and adds extra height, making the gap after this row larger than spacedBy(8.dp).

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val menuWidth = 200.dp
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable { expanded = true }
                    .menuAnchor()
                    .clip(shape = RoundedCornerShape(14.dp))
                    .background(color = Color(0x80E6E7E9))
                    .padding(16.dp),
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Color(0xFF2F70BC),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(nameRes),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = ColorTextPrimary,
                    ),
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = if (use24Hour) "24 hour" else "12 hour",
                    style = customizedTextStyle(
                        color = Color(0xFF2F70BC), fontSize = 15, fontWeight = 400
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "",
                    tint = Color(0xFFC4C6CA)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(x = maxWidth - menuWidth, y = 0.dp),
                modifier = Modifier.width(menuWidth),
                containerColor = Color.White,
                shape = RoundedCornerShape(8.dp),
            ) {
                // 24 hour
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "24 ${stringResource(R.string.hour)}",
                            style = customizedTextStyle(
                                fontSize = 15, fontWeight = 500
                            ),
                            color = ColorTextPrimary,
                        )
                    },
                    onClick = {
                        onCheckedChange(true)
                        expanded = false
                    },
                    modifier = Modifier.wrapContentWidth()
                )

                // 12 hour
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "12 ${stringResource(R.string.hour)}",
                            style = customizedTextStyle(
                                fontSize = 15, fontWeight = 500
                            ),
                            color = ColorTextPrimary,
                        )
                    },
                    onClick = {
                        onCheckedChange(false)
                        expanded = false
                    },
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }

    }

}