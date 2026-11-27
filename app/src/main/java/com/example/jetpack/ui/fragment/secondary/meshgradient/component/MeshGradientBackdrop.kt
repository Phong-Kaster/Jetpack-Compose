package com.example.jetpack.ui.fragment.secondary.meshgradient.component

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.MeshGradientPainter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.currentStateAsState
import com.example.jetpack.ui.fragment.secondary.meshgradient.model.MeshColors
import com.example.jetpack.ui.fragment.secondary.meshgradient.model.MeshLattice
import com.example.jetpack.ui.fragment.secondary.meshgradient.model.MeshPalette

/** How long a palette change takes to fade from the old colours to the new ones. */
private const val COLOR_FADE_MILLIS = 450

/** The mesh is drawn this many times smaller, then stretched back to full size (see drawMeshDownscaled). */
private const val MESH_DOWNSCALE = 10f

/*
 * --- How the liquid backdrop moves (simple story) ---
 * 1. A "clock" number runs from 0 to 1 over periodMillis, then jumps back to 0 and runs again.
 *    Because every knot moves in whole waves per loop (see MeshLattice.position), 1 and 0 look
 *    exactly the same, so the jump is invisible.
 * 2. The MeshGradientPainter block reads the clock, the wobble and the five colours ONLY inside
 *    its block. That block runs while drawing, so a new clock value just redraws the picture; the
 *    composable itself never recomposes 60 times a second.
 * 3. The clock only ticks when it is worth it: the caller asked for motion, the screen is at
 *    least STARTED (visible), and the user did not turn animations off in Developer options /
 *    Accessibility. When it stops, it stays where it was and continues from there later.
 */
/**
 * A full-size animated mesh gradient with a light scrim on top and your [content] above it.
 *
 * Example:
 * ```kotlin
 * MeshGradientBackdrop(
 *     palette = MeshPalette.Aurora,
 *     modifier = Modifier.fillMaxSize(),
 *     content = { Text(text = "Hello") },
 * )
 * ```
 *
 * @param palette which five colours to paint with; switching fades smoothly
 * @param animate false freezes the picture (handy for previews and screenshot tests)
 * @param periodMillis how long one full loop of the motion lasts
 * @param wobble how far the inner knots travel, from 0 to [MeshLattice.MAX_WOBBLE]
 * @param hasSmoothColor true = Catmull-Rom colour blending (smoother), false = bilinear (cheaper)
 * @param showVertices true draws the net and its knots on top, to see how the mesh works
 * @author Phong-Kaster
 */
@Composable
fun MeshGradientBackdrop(
    palette: MeshPalette,
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    periodMillis: Int = 10_000,
    wobble: Float = 0.10f,
    hasSmoothColor: Boolean = true,
    showVertices: Boolean = false,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val lifecycleState by LocalLifecycleOwner.current.lifecycle.currentStateAsState()
    val reduceMotion = rememberReduceMotion()
    val running = animate && !reduceMotion && lifecycleState.isAtLeast(Lifecycle.State.STARTED)

    val clock = remember { Animatable(initialValue = 0f) }
    val wobbleState = rememberUpdatedState(newValue = wobble.coerceIn(0f, MeshLattice.MAX_WOBBLE))
    val topStart = animateColorAsState(targetValue = palette.colors.topStart, animationSpec = tween(COLOR_FADE_MILLIS), label = "topStart")
    val topEnd = animateColorAsState(targetValue = palette.colors.topEnd, animationSpec = tween(COLOR_FADE_MILLIS), label = "topEnd")
    val bottomStart = animateColorAsState(targetValue = palette.colors.bottomStart, animationSpec = tween(COLOR_FADE_MILLIS), label = "bottomStart")
    val bottomEnd = animateColorAsState(targetValue = palette.colors.bottomEnd, animationSpec = tween(COLOR_FADE_MILLIS), label = "bottomEnd")
    val glow = animateColorAsState(targetValue = palette.colors.glow, animationSpec = tween(COLOR_FADE_MILLIS), label = "glow")

    // Reads every State inside the draw block, so animation frames only redraw (see story above).
    val painter = remember(
        hasSmoothColor,
        calculation = {
            MeshGradientPainter(
                rows = MeshLattice.ROWS,
                columns = MeshLattice.COLUMNS,
                hasBicubicColor = hasSmoothColor,
                block = {
                    val time = clock.value
                    val currentWobble = wobbleState.value
                    val colors = MeshColors(
                        topStart = topStart.value,
                        topEnd = topEnd.value,
                        bottomStart = bottomStart.value,
                        bottomEnd = bottomEnd.value,
                        glow = glow.value,
                    )
                    for (row in 0..rows) {
                        for (column in 0..columns) {
                            setVertex(
                                row = row,
                                column = column,
                                position = MeshLattice.position(row = row, column = column, time = time, wobble = currentWobble),
                                color = MeshLattice.color(row = row, column = column, colors = colors, time = time),
                            )
                        }
                    }
                },
            )
        },
    )

    // Restarts when the clock should start/stop or the loop length changes, and carries on
    // from the current value so changing the speed never makes the picture jump.
    LaunchedEffect(
        running,
        periodMillis,
        block = {
            if (!running) return@LaunchedEffect
            while (true) {
                val remainingMillis = ((1f - clock.value) * periodMillis).toInt()
                clock.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = remainingMillis, easing = LinearEasing),
                )
                clock.snapTo(targetValue = 0f)
            }
        },
    )

    Box(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .drawBehind(onDraw = { drawMeshDownscaled(painter = painter) }),
            )

            if (showVertices) {
                Canvas(
                    modifier = Modifier.matchParentSize(),
                    onDraw = { drawLattice(time = clock.value, wobble = wobbleState.value) },
                )
            }

            // A light dark veil so white text stays readable on the brightest colours.
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(color = Color.Black.copy(alpha = 0.18f)),
            )

            content()
        },
    )
}

/*
 * --- Why the mesh is drawn small and stretched (simple story) ---
 * MeshGradientPainter cuts every patch into tiny triangles, about one every 8 pixels, and works
 * out each triangle's corners and colours on the CPU, on every frame. Full-screen on a 1080 px
 * phone that is ~26,000 points per frame: on a Galaxy A13 one frame took 1.7 s.
 *
 * A gradient has no sharp edges, so it does not need that much detail. We tell the painter the
 * canvas is MESH_DOWNSCALE times smaller (about 50x fewer points), then scale the canvas
 * up by the same amount. The triangles are still drawn by the GPU at full resolution, so the
 * picture stays smooth, only cheaper.
 */
/**
 * Draws [painter] at 1 / [MESH_DOWNSCALE] of the size, scaled back up to fill the whole area.
 *
 * @author Phong-Kaster
 */
private fun DrawScope.drawMeshDownscaled(painter: Painter) {
    scale(
        scale = MESH_DOWNSCALE,
        pivot = Offset.Zero,
        block = {
            with(
                painter,
                block = { draw(size = size / MESH_DOWNSCALE) },
            )
        },
    )
}

/**
 * Draws the net: thin lines between neighbouring knots and a dot on every knot.
 * Inner (moving) knots are drawn bigger than the pinned border knots.
 *
 * @author Phong-Kaster
 */
private fun DrawScope.drawLattice(time: Float, wobble: Float) {
    // Turns a normalized (0..1) mesh position into screen pixels.
    fun toPixels(row: Int, column: Int): Offset {
        val normalized = MeshLattice.position(row = row, column = column, time = time, wobble = wobble)
        return Offset(x = normalized.x * size.width, y = normalized.y * size.height)
    }

    for (row in 0..MeshLattice.ROWS) {
        for (column in 0..MeshLattice.COLUMNS) {
            val here = toPixels(row = row, column = column)
            if (column < MeshLattice.COLUMNS) {
                drawLine(color = Color.White.copy(alpha = 0.45f), start = here, end = toPixels(row = row, column = column + 1), strokeWidth = 1.dp.toPx())
            }
            if (row < MeshLattice.ROWS) {
                drawLine(color = Color.White.copy(alpha = 0.45f), start = here, end = toPixels(row = row + 1, column = column), strokeWidth = 1.dp.toPx())
            }
        }
    }

    for (row in 0..MeshLattice.ROWS) {
        for (column in 0..MeshLattice.COLUMNS) {
            val radius = if (MeshLattice.isInterior(row = row, column = column)) 6.dp.toPx() else 3.dp.toPx()
            drawCircle(color = Color.White, radius = radius, center = toPixels(row = row, column = column))
        }
    }
}

/**
 * True when the user turned animations off (Settings → Accessibility "Remove animations", or
 * Developer options "Animator duration scale" = Off). Updates live if the setting changes while
 * the screen is open.
 *
 * @author Phong-Kaster
 */
@Composable
private fun rememberReduceMotion(): Boolean {
    val context = LocalContext.current
    var reduceMotion by remember(context, calculation = { mutableStateOf(isAnimationOff(context = context)) })

    DisposableEffect(
        context,
        effect = {
            val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
                override fun onChange(selfChange: Boolean) {
                    reduceMotion = isAnimationOff(context = context)
                }
            }
            context.contentResolver.registerContentObserver(
                Settings.Global.getUriFor(Settings.Global.ANIMATOR_DURATION_SCALE),
                false,
                observer,
            )
            onDispose(onDisposeEffect = { context.contentResolver.unregisterContentObserver(observer) })
        },
    )

    return reduceMotion
}

/**
 * Reads the system "animator duration scale"; 0 means the user switched animations off.
 *
 * @author Phong-Kaster
 */
private fun isAnimationOff(context: Context): Boolean {
    val scale = Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f)
    return scale == 0f
}

/**
 * Preview of the frozen Aurora backdrop.
 *
 * @author Phong-Kaster
 */
@Preview
@Composable
private fun PreviewMeshGradientBackdrop() {
    MeshGradientBackdrop(
        palette = MeshPalette.Aurora,
        animate = false,
        modifier = Modifier.fillMaxSize(),
    )
}

/**
 * Preview of the frozen Sunset backdrop with the net drawn on top.
 *
 * @author Phong-Kaster
 */
@Preview(name = "With vertices")
@Composable
private fun PreviewMeshGradientBackdropWithVertices() {
    MeshGradientBackdrop(
        palette = MeshPalette.Sunset,
        animate = false,
        showVertices = true,
        modifier = Modifier.fillMaxSize(),
    )
}
