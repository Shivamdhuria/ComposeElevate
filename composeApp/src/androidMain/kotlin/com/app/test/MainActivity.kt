package com.app.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.MeshGradient
import io.github.shivamdhuria.elevate.stroked
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StrokedImagesPreview()
        }
    }
}


@Preview
@Composable
fun StrokedImagesPreview() {

    var visible by remember { mutableStateOf(true) }

    val infiniteTransition = rememberInfiniteTransition()

    // 2. Animate a float value from 0 to -20 (floating up, negative is upward if you consider y=0 top)
    //    Then it reverses, creating a gentle up and down movement
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        delay(5000)
        visible = true
    }

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
        val maxWidth = (constraints.maxWidth.toFloat() + 100f)

        // Orbiting images with different parameters.
        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.6f,
            orbitSpeed = 14000, // 10 seconds per orbit.
            initialAngle = 0f
        ) {
            OtherImage(
                R.drawable.donut,
                strokeSize = 8.dp,
                color = Color(0xFFb2ebff),
                size = 120.dp
            )

        }

        // Orbiting images with different parameters.
        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.6f,
            orbitSpeed = 14000, // 10 seconds per orbit.
            initialAngle = 60f
        ) {
            OtherImage(
                R.drawable.heart,
                strokeSize = 10.dp,
                color = Color.White,
                size = 80.dp
            )

        }


        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.5f,
            orbitSpeed = 14000, // 15 seconds per orbit.
            initialAngle = 120f
        ) {
            OtherImage(R.drawable.coffee, strokeSize = 10.dp, color = Color.White, size = 100.dp)
        }

        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.5f,
            orbitSpeed = 14000, // 8 seconds per orbit.
            initialAngle = 240f
        ) {
            OtherImage(R.drawable.coffee, strokeSize = 10.dp, color = Color.White, size = 100.dp)
        }

        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.5f,
            orbitSpeed = 14000, // 8 seconds per orbit.
            initialAngle = 180f
        ) {
            OtherImage(R.drawable.donut, strokeSize = 10.dp, color = Color.White, size = 100.dp)
        }

        SmoothOrbitingImage(
            orbitRadius = maxWidth * 0.5f,
            orbitSpeed = 12000, // 8 seconds per orbit.
            initialAngle = 300f
        ) {
            OtherImage(R.drawable.chill, strokeSize = 10.dp, color = Color.White, size = 160.dp)
        }

        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(spring()),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Other Image",
                modifier = Modifier
                    .height(70.dp)
                    .offset(y = offsetY.dp)
                    .stroked(
                        30.dp,
                        Color(0xFFa2d2ff),
                        edgeSmoothness = 32,
                        strokeAlpha = 0.08f,
                        featheringLevels = persistentListOf(1.0f, 0.7f, 0.4f, 0.1f)
                    ),
                colorFilter = ColorFilter.tint(Color.White),
                contentScale = ContentScale.FillHeight

            )

        }
    }
}
