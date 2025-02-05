package com.app.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import androidx.compose.ui.geometry.Offset

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
