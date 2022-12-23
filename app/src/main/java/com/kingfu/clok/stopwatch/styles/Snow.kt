package com.kingfu.clok.stopwatch.styles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchTime


@Preview(showBackground = true)
@Composable
fun Snow() {
    val configurationOrientation = LocalConfiguration.current.orientation

    Box(
        modifier = Modifier
            .fillMaxHeight(0.08f)
            .fillMaxWidth(if (stopwatchTime < 3_600_000) 0.625f else 0.85f)
            .background(Color.Cyan.copy(0.2f))
    ) {

//            Column(Modifier.fillMaxWidth().background(Color.Blue.copy(0.2f))) {
//                Text(text = "Hello World", fontSize = 44.sp, color = Color.Red)
//                Text(text = "Hello World", fontSize = 44.sp, color = Color.Red)
//
//            }

        Canvas(modifier = Modifier.size(7.dp), onDraw = {
            drawCircle(Color.Red)
        })


    }

}