package com.app.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun StrokedImage(
    painter: Painter,
    strokeWidth: Dp = 8.dp,
    strokeColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    // Convert stroke width from Dp to pixels.
    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }

    // Define eight offsets: left, right, top, bottom, and the four diagonals.
    val offsets = listOf(
        Offset(-strokePx, 0f),    // left
        Offset(strokePx, 0f),     // right
        Offset(0f, -strokePx),    // top
        Offset(0f, strokePx),     // bottom
        Offset(-strokePx, -strokePx), // top-left
        Offset(strokePx, -strokePx),  // top-right
        Offset(-strokePx, strokePx),  // bottom-left
        Offset(strokePx, strokePx)    // bottom-right
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Draw a tinted copy at each offset.
        offsets.forEach { offset ->
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.offset {
                    IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
                },
                colorFilter = ColorFilter.tint(strokeColor, BlendMode.SrcIn)
            )
        }
        // Draw the original image on top.
        Image(
            painter = painter,
            contentDescription = null
        )
    }
}


@Composable
fun Modifier.strokeAlongPath(
    strokeWidth: Dp = 8.dp,
    strokeColor: Color = Color.White
): Modifier {
    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }

    return this.then(
        Modifier.drawWithCache {
            val offsets = listOf(
                Offset(-strokePx, 0f),      // left
                Offset(strokePx, 0f),       // right
                Offset(0f, -strokePx),      // top
                Offset(0f, strokePx),       // bottom
                Offset(-strokePx, -strokePx), // top-left
                Offset(strokePx, -strokePx),  // top-right
                Offset(-strokePx, strokePx),  // bottom-left
                Offset(strokePx, strokePx)    // bottom-right
            )

            onDrawWithContent {
                // Draw the stroke effect
                offsets.forEach { offset ->
                    withTransform({
                        translate(offset.x, offset.y)
                    }) {
                        // Explicitly reference the outer DrawScope
                        //
                        this@onDrawWithContent.drawContent()
                    }
                }

                // Draw the original content on top
                drawContent()
            }
        }
    )
}


// Modifier extension function to add stroked effect to Image
fun Modifier.strokedImage(
    strokeWidth: Dp = 8.dp,
    strokeColor: Color = Color.White
): Modifier {
    return this.drawBehind {
        val strokePx = strokeWidth.toPx()
        val offsets = listOf(
            Offset(-strokePx, 0f),    // left
            Offset(strokePx, 0f),     // right
            Offset(0f, -strokePx),    // top
            Offset(0f, strokePx),     // bottom
            Offset(-strokePx, -strokePx), // top-left
            Offset(strokePx, -strokePx),  // top-right
            Offset(-strokePx, strokePx),  // bottom-left
            Offset(strokePx, strokePx)    // bottom-right
        )

        offsets.forEach { offset ->

            offsets.forEach { offset ->
                drawRect(
                    color = strokeColor,
                    topLeft = offset,
                    size = size,
                    alpha = 0.5f
                )
            }

//            drawIntoCanvas { canvas ->
//                canvas.save()
//                canvas.translate(offset.x, offset.y)
//
//                // Redraw the content with tint
//                drawContext.draw(
//                    colorFilter = ColorFilter.tint(strokeColor, BlendMode.SrcIn)
//                )
//
//                canvas.restore()
//            }
        }
    }
}
@Composable
fun Modifier.workingButMultipleHoles(
    strokeWidth: Dp = 2.dp,
    strokeColor: Color = Color.White
): Modifier {
    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }

    return this.then(
        Modifier.drawWithCache {
            val offsets = listOf(
                Offset(-strokePx, 0f),      // left
                Offset(strokePx, 0f),       // right
                Offset(0f, -strokePx),      // top
                Offset(0f, strokePx),       // bottom
                Offset(-strokePx, -strokePx), // top-left
                Offset(strokePx, -strokePx),  // top-right
                Offset(-strokePx, strokePx),  // bottom-left
                Offset(strokePx, strokePx)    // bottom-right
            )

            onDrawWithContent {
                offsets.forEach { offset ->
                    withTransform({
                        translate(offset.x, offset.y)
                    }) {
                        // Wrap drawContent in drawIntoCanvas and save a layer with a Paint that tints.
                        drawIntoCanvas { canvas ->
                            // Create a Paint with your color filter
                            val paint = Paint().apply {
                                colorFilter = ColorFilter.tint(strokeColor, BlendMode.SrcIn)
                            }
                            // Define a layer covering the whole drawing area.
                            val rect = Rect(Offset.Zero, size)
                            canvas.saveLayer(rect, paint)
                            // Draw the original content into the tinted layer.
                            this@onDrawWithContent.drawContent()
                            canvas.restore()
                        }
                    }
                }

                // Draw the original content on top
                drawContent()
            }
        }
    )
}

fun Modifier.stroked(
    strokeWidth: Dp = 8.dp,
    strokeColor: Color = Color.White,
    // Increase this for a smoother edge.
    edgeSmoothness: Int = 32
): Modifier = this.then(
    Modifier.drawWithCache {
        // Convert stroke width from Dp to pixels.
        val strokePx = strokeWidth.toPx()

        // Compute offsets evenly distributed around a circle.
        // More copies means a smoother stroke.
        val offsets = (0 until edgeSmoothness).map { i ->
            val angle = 2 * PI * i / edgeSmoothness
            Offset(
                x = (strokePx * cos(angle)).toFloat(),
                y = (strokePx * sin(angle)).toFloat()
            )
        }

        onDrawWithContent {
            // For each computed offset, draw a tinted copy of the content.
            offsets.forEach { offset ->
                withTransform({
                    translate(offset.x, offset.y)
                }) {
                    drawIntoCanvas { canvas ->
                        // Create a Paint that tints using the provided color.
                        val paint = Paint().apply {
                            isAntiAlias = true
                            filterQuality = FilterQuality.High
                            colorFilter = ColorFilter.tint(strokeColor, BlendMode.SrcIn)
                        }
                        val rect = Rect(Offset.Zero, size)
                        // Save a layer with the tint applied.
                        canvas.saveLayer(rect, paint)
                        // Draw the original content into the tinted layer.
                        this@onDrawWithContent.drawContent()
                        canvas.restore()
                    }
                }
            }
            // Finally, draw the original content on top.
            drawContent()
        }
    }
)


fun Modifier.stroked5(
    strokeWidth: Dp = 8.dp,
    strokeColor: Color = Color.White,
    strokeAlpha: Float = 0.3f,
    // Feathering levels for smoother edge gradient
    featheringLevels: List<Float> = listOf(1.0f, 0.8f, 0.6f, 0.4f, 0.2f),
    // Increase this for a smoother edge.
    edgeSmoothness: Int = 32
): Modifier = this.then(
    Modifier.drawWithCache {
        // Convert stroke width from Dp to pixels.
        val strokePx = strokeWidth.toPx()/2

        // Compute offsets evenly distributed around a circle.
        val offsets = (0 until edgeSmoothness).map { i ->
            val angle = 2 * PI * i / edgeSmoothness
            Offset(
                x = (strokePx * cos(angle)).toFloat(),
                y = (strokePx * sin(angle)).toFloat()
            )
        }

        onDrawWithContent {
            // Apply feathered strokes
            featheringLevels.forEach { featherLevel ->
                offsets.forEach { offset ->
                    withTransform({
                        translate(
                            offset.x * featherLevel,
                            offset.y * featherLevel
                        )
                    }) {
                        drawIntoCanvas { canvas ->
                            val paint = Paint().apply {
                                isAntiAlias = true
                                filterQuality = FilterQuality.High
                                colorFilter = ColorFilter.tint(
                                    strokeColor.copy(
                                        alpha = strokeAlpha * featherLevel
                                    ),
                                    BlendMode.SrcIn
                                )
                            }
                            val rect = Rect(Offset.Zero, size)
                            canvas.saveLayer(rect, paint)
                            this@onDrawWithContent.drawContent()
                            canvas.restore()
                        }
                    }
                }
            }

            // Finally, draw the original content on top.
            drawContent()
        }
    }
)

fun Modifier.stroked6(
    strokeWidth: Dp =8.dp,
    strokeColor: Color = Color.White,
    strokeAlpha: Float = 0.3f,
    // Feathering levels for a smoother edge gradient.
    featheringLevels: List<Float> = listOf(1.0f, 0.8f, 0.6f, 0.4f, 0.2f),
    // Increase this for a smoother edge.
    edgeSmoothness: Int = 32
): Modifier = this
    // Add padding so that the stroke drawing has room and doesn’t overlap adjacent composables.
    .padding(strokeWidth / 2)
    .then(
        Modifier.drawWithCache {
            // Convert stroke width from Dp to pixels (dividing by 2 to get the radius).
            val strokePx = strokeWidth.toPx() / 2

            // Compute offsets evenly distributed around a circle.
            val offsets = (0 until edgeSmoothness).map { i ->
                val angle = 2 * PI * i / edgeSmoothness
                Offset(
                    x = (strokePx * cos(angle)).toFloat(),
                    y = (strokePx * sin(angle)).toFloat()
                )
            }

            onDrawWithContent {
                // Draw the feathered stroke by translating the content multiple times.
                featheringLevels.forEach { featherLevel ->
                    offsets.forEach { offset ->
                        withTransform({
                            translate(
                                offset.x * featherLevel,
                                offset.y * featherLevel
                            )
                        }) {
                            drawIntoCanvas { canvas ->
                                val paint = Paint().apply {
                                    isAntiAlias = true
                                    filterQuality = FilterQuality.High
                                    colorFilter = ColorFilter.tint(
                                        strokeColor.copy(alpha = strokeAlpha * featherLevel),
                                        BlendMode.SrcIn
                                    )
                                }
                                val rect = Rect(Offset.Zero, size)
                                canvas.saveLayer(rect, paint)
                                // Draw the content to create a stroke effect.
                                this@onDrawWithContent.drawContent()
                                canvas.restore()
                            }
                        }
                    }
                }
                // Finally, draw the original content on top.
                drawContent()
            }
        }
    )
