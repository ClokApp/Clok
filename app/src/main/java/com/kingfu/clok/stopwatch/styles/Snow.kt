package com.kingfu.clok.stopwatch.styles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Preview(showBackground = true)
@Composable
fun Snow() {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.08f)
            .fillMaxWidth(0.6f)
            .background(Color.Transparent)
    ) {

            Column(Modifier.fillMaxWidth().background(Color.Blue.copy(0.2f))) {
                Text(text = "Hello World", fontSize = 44.sp, color = Color.Red)
                Text(text = "Hello World", fontSize = 44.sp, color = Color.Red)

            }


    }
}