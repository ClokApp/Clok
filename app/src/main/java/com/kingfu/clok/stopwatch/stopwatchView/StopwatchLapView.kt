package com.kingfu.clok.stopwatch.stopwatchView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.customFontSize


@Composable
fun StopwatchLapView(
    vm: StopwatchViewModel,
    lazyColumnState: LazyListState,
    configurationOrientation: Int
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .alpha(if (vm.lapCounter > 0 || configurationOrientation == Configuration.ORIENTATION_LANDSCAPE) 1f else 0f)
            .fillMaxHeight(if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 0.65f else 1f)
            .fillMaxWidth(if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 0.8f else 0.5f)
            .padding(
                vertical = 0.dp,
                horizontal = if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 0.dp else 40.dp
            ),

        ) {

        Row(Modifier.padding(10.dp)) {
            LapLabel(name = "Lap", weight = 0.20f)
            LapLabel(name = "Lap times", weight = 0.40f)
            LapLabel(name = "Total times", weight = 0.40f)
        }

        Divider(
            color = Color.DarkGray,
            thickness = 0.8.dp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        )
        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyColumnState,
            ) {
                items(vm.lapNumber.size) { index ->
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                    ) {
                        LapContent(name = vm.getLapNumber(index), weight = 0.20f, alpha = 0.50f)
                        LapContent(name = vm.getLapTimes(index), weight = 0.40f, alpha = 0.70f)
                        LapContent(name = vm.getLapTotalTimes(index), weight = 0.40f, alpha = 0.90f)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Black00
                            )
                        )
                    )
                    .matchParentSize()
            )
        }
    }

}

@Composable
fun RowScope.LapLabel(name: String, weight: Float) {
    Text(
        text = name,
        modifier = Modifier.weight(weight),
        fontSize = customFontSize(textUnit = 16.sp),
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = Color.White.copy(0.70f),
        textAlign = TextAlign.Center
    )
}

@Composable
fun RowScope.LapContent(name: String, weight: Float, alpha: Float ){
    Text(
        text = name,
        fontSize = customFontSize(textUnit = 16.sp),
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = Color.White.copy(alpha = alpha),
        modifier = Modifier.weight(weight = weight),
        textAlign = TextAlign.Center
    )

}



