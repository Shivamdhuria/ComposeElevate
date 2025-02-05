package com.app.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import kotlin.math.roundToInt

@Composable
fun Drafts(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {

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
            modifier = Modifier
                .size(150.dp)

                .stroked7(40.dp, Color.Magenta, strokeAlpha = 1f),
        )
    }
}