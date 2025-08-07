package com.example.jetpack.ui.fragment.tutorial.component

import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetpack.R
import com.example.jetpack.ui.theme.ColorUVIndexLow
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.animationInfiniteColor
import com.example.jetpack.ui.theme.animationInfiniteFloat
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil.toIntOffset

@Composable
fun TutorialNaziBadge() {
    /*define animation*/
    val tutorial = LocalTutorial.current
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = ColorUVIndexLow,
        targetValue = PrimaryColor,
        animationSpec = animationInfiniteColor,
        label = "animatedColor"
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = animationInfiniteFloat,
        label = "scale"
    )


    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.1F))
            .drawBehind {
                clipPath(
                    Path().apply {
                        addOval(tutorial.naziBadgeRect.inflate(6.dp.toPx()))
                    },
                    clipOp = ClipOp.Difference
                ) {
                    drawRect(color = Color.Black.copy(0.6f))
                }
            }
    ) {
        val (badge, text) = createRefs()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset { tutorial.naziBadgeRect.topLeft.toIntOffset() }
                .clip(shape = CircleShape)
                .size(24.dp)
                .clickable { tutorial.currentTutorial = 1 }
                .constrainAs(badge) {}
        ) {
            Image(
                painter = painterResource(R.drawable.ic_bundeswehr),
                contentDescription = stringResource(R.string.icon),
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .offset {
                    tutorial.naziBadgeRect.bottomLeft
                        .toIntOffset()
                        .copy(x = 0)
                }
                .clip(shape = RoundedCornerShape(20.dp))
                .clickable {
                    Toast.makeText(
                        context,
                        "Tap to explore your baby’s growth week by week",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .constrainAs(text) {
                    top.linkTo(badge.bottom, margin = -20.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }
        ) {
            Image(
                painter = painterResource(R.drawable.ic_polygon),
                contentDescription = stringResource(R.string.icon),
                modifier = Modifier
                    .size(14.dp)
                    .offset(x = (-20).dp, y = 5.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(220.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(5.dp)
            ) {
                // Tap to explore your baby’s growth week by week
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = customizedTextStyle(
                                fontSize = 14,
                                fontWeight = 400,
                                color = animatedColor
                            ).toSpanStyle()
                        ) {
                            append(text = "Tap")
                        }
                        append(text = "\t")
                        withStyle(
                            style = customizedTextStyle(
                                fontSize = 14,
                                fontWeight = 400,
                                color = Color.Black
                            ).toSpanStyle()
                        ) {
                            append(text = "to explore your baby’s growth week by week")
                        }
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier,
                )


                // GIF
                Image(
                    painter = rememberAsyncImagePainter(
                        imageLoader = imageLoader,
                        model = ImageRequest
                            .Builder(context)
                            .data(data = R.drawable.gif_tap)
                            .apply(
                                block = { size(Size.ORIGINAL) }
                            )
                            .build(),
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                )


                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Skip",
                        style = customizedTextStyle(
                            color = Color(0xFF595959),
                            fontSize = 14,
                            fontWeight = 400
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .clickable {
                                tutorial.currentTutorial = 1
                            }
                            .padding(5.dp)
                    )

                    Text(
                        text = stringResource(R.string.ok),
                        style = customizedTextStyle(
                            color = Color(0xFFFF7B7B),
                            fontSize = 14,
                            fontWeight = 600
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .clickable {
                                tutorial.currentTutorial = 1
                            }
                            .padding(5.dp)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun PreviewTutorialNaziBadge() {
    TutorialNaziBadge()
}