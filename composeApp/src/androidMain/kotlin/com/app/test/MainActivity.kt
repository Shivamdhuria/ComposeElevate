package com.app.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.MeshGradient
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Preview
@Composable
fun Test() {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()

            .shaderBackground(
                MeshGradient(
                    arrayOf(Color(0xFFFFb8f7), Color(0xFFffe3fb), Color(0xFFf9b2ff)),
                    scale = 0.5f
                )
            ), contentAlignment = Alignment.Center
    ) {
        // We use the available width/height for our start values.
        val maxWidth = constraints.maxWidth.toFloat()

        // Orbiting images with different parameters.
        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.3f,
            orbitSpeed = 10000, // 10 seconds per orbit.
            initialAngle = 0f
        ) {
            StrokedDonut()
        }

        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.4f,
            orbitSpeed = 15000, // 15 seconds per orbit.
            initialAngle = 120f
        ) {
            OtherImage()
        }

        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.5f,
            orbitSpeed = 8000, // 8 seconds per orbit.
            initialAngle = 240f
        ) {
            AnotherImage()
        }

        // Use a Box with contentAlignment = Alignment.Center so that the image is
        // always centered in the available space. Then apply the computed offset.
//        AnimatedStrokedDonut(offsetX, offsetY) {
//            StrokedDonut()
//        }


    }

}

@Composable
fun StrokedImage2(
    painter: Painter,
    strokeWidth: Dp = 8.dp,
    strokeColor: Color = Color.White,
    // Increase this value for a smoother edge.
    // For rough shapes, you might set this higher (e.g. 64 or even 128) to get a smoother stroke.
    edgeSmoothness: Int = 32,
    modifier: Modifier = Modifier
) {
    // Convert stroke width from Dp to pixels.
    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }

    // Calculate offsets evenly distributed around a circle using the edgeSmoothness value.
    // More copies means a smoother, less segmented stroke.
    val offsets = (0 until edgeSmoothness).map { i ->
        val angle = 2 * PI * i / edgeSmoothness
        Offset(
            x = (strokePx * cos(angle)).toFloat(),
            y = (strokePx * sin(angle)).toFloat()
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Draw a tinted copy at each calculated offset.
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
fun AnimatedStrokedDonut(offsetX: Float, offsetY: Float, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Use key(Unit) to create a separate recomposition boundary.
        key(Unit) {
            // Only the wrapper with graphicsLayer is updated during animation.
            Box(modifier = Modifier.graphicsLayer {
                translationX = offsetX
                translationY = offsetY
            }) {
                content()
            }
        }
    }
}



// A simple linear interpolation helper.
private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}