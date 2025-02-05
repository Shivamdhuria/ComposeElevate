package com.app.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.BlackCherryCosmos
import com.mikepenz.hypnoticcanvas.shaders.MeshGradient
import com.mikepenz.hypnoticcanvas.shaders.RainbowWater
import com.mikepenz.hypnoticcanvas.shaders.Stage
import kotlin.math.PI
import kotlin.math.cos
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
    Box(
        Modifier
            .fillMaxSize()
            .shaderBackground(
                MeshGradient(
                    arrayOf(Color(0xFFFFb8f7), Color(0xFFffe3fb), Color(0xFFb9b2ff)),
                    scale = 1f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(0.dp)) {

            val painter = rememberAsyncImagePainter(model = R.drawable.heart)
            val painter2 = rememberAsyncImagePainter(model = R.drawable.donut)
//            StrokedImage(
//                painter = painter,
//                strokeWidth = 1.dp,
//                strokeColor = Color.Blue,
//                modifier = Modifier.size(30.dp)
//            )

//            Image(
//                painter = painterResource(
//                    id = R.drawable.donut
//                ),
//                contentDescription = null,
//                modifier = Modifier.size(150.dp).workingButMultipleHoles(90.dp, Color.Blue),
//            )

//            StrokedImage2(
//                painter = painter,
//                strokeWidth = 20.dp,
//                strokeColor = Color.Blue,
//                modifier = Modifier.size(450.dp),
//                edgeSmoothness = 50
//            )
//            Image(
//                painter = painter,
//                contentDescription = null,
//                modifier = Modifier.size(100.dp).strokeNew(
//                    strokeWidth = 8.dp,
//                    strokeColor = Color.White,
//                    edgeSmoothness = 64
//                ).size(120.dp)
//            )
//            StrokedImage2(
//                painter = painter2,
//                strokeWidth = 90.dp,
//                strokeColor = Color.Black,
//                modifier = Modifier.size(150.dp),
//                edgeSmoothness = 128
//            )

//            Image(
//                painter = painterResource(
//                    id = R.drawable.donut
//                ),
//                contentDescription = null,
//                modifier = Modifier.size(150.dp).stroked(8.dp, Color.Magenta),
//            )
            Image(
                painter = painterResource(
                    id = R.drawable.donut
                ),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
            )
//            Image(
//                painter = painterResource(
//                    id = R.drawable.donut
//                ),
//                contentDescription = null,
//                modifier = Modifier.size(150.dp).stroked6(20.dp, Color.Magenta, strokeAlpha = 0.03f),
//            )
            Image(
                painter = painterResource(
                    id = R.drawable.donut
                ),
                contentDescription = null,
                modifier = Modifier.size(150.dp).stroked7(40.dp, Color.Magenta, strokeAlpha = 1f),
            )
        }

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