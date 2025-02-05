package com.app.test

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

// A reusable composable that animates its content along an orbit.

@Composable
fun SmoothOrbitingImage(
    orbitRadius: Float,
    orbitSpeed: Int,
    initialAngle: Float = 0f,
    content: @Composable () -> Unit
) {
    // Using a mutable state to hold the current angle.
    val angleState = remember { mutableStateOf(initialAngle) }

    // Launch a coroutine that updates the angle every frame.
    LaunchedEffect(orbitSpeed, initialAngle) {
        // orbitSpeed in milliseconds converted to nanoseconds.
        val orbitSpeedNanos = orbitSpeed * 1_000_000L
        // Remember the start time.
        var startTime = withFrameNanos { it }
        while (true) {
            // Get the current frame time.
            val currentTime = withFrameNanos { it }
            val elapsed = currentTime - startTime
            // Calculate the fraction of the orbit that has completed.
            val fraction = (elapsed % orbitSpeedNanos).toFloat() / orbitSpeedNanos.toFloat()
            // Update the angle (in degrees). One full orbit equals 360 degrees.
            angleState.value = initialAngle + 360f * fraction
        }
    }

    // Convert the current angle from degrees to radians.
    val angleRad = Math.toRadians(angleState.value.toDouble())
    // Calculate offsets based on the orbitRadius.
    val offsetX = orbitRadius * cos(angleRad).toFloat()
    val offsetY = orbitRadius * sin(angleRad).toFloat()

    // Apply the computed translation using graphicsLayer.
    Box(
        modifier = Modifier.graphicsLayer {
            translationX = offsetX
            translationY = offsetY
        },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


@Composable
fun OrbitingImage(
    orbitRadius: Float,
    orbitSpeed: Int,
    initialAngle: Float = 0f,
    content: @Composable () -> Unit
) {
    // Create an infinite animation for the angle.
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = initialAngle,
        targetValue = initialAngle + 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = orbitSpeed, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Convert the current angle (in degrees) to radians.
    val angleRad = Math.toRadians(angle.toDouble())
    // Calculate x and y offset based on the orbit radius.
    val offsetX = orbitRadius * cos(angleRad).toFloat()
    val offsetY = orbitRadius * sin(angleRad).toFloat()

    // The content is centered and then translated relative to the center.
    Box(
        modifier = Modifier.graphicsLayer {
            translationX = offsetX
            translationY = offsetY
        },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


// For demonstration purposes, here’s a dummy "stroked" donut.
// Replace this with your actual StrokedDonut() composable.
@Composable
fun StrokedDonut() {
    Image(
        painter = painterResource(id = R.drawable.donut),
        contentDescription = "Donut",
        modifier = Modifier.size(100.dp)
        // If you have a custom stroke modifier, chain it here.
        .stroked8(10.dp, Color.White, edgeSmoothness = 6)
    )
}

// Another example image
@Composable
fun OtherImage() {
    Image(
        painter = painterResource(id = R.drawable.donut),
        contentDescription = "Other Image",
        modifier = Modifier.size(80.dp)
    )
}

// And yet another example image
@Composable
fun AnotherImage() {
    Image(
        painter = painterResource(id = R.drawable.donut),
        contentDescription = "Another Image",
        modifier = Modifier.size(120.dp)
    )
}

