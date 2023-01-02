package com.kingfu.clok.timer.backgroundEffects

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import com.kingfu.clok.ui.theme.Black00
import kotlin.math.sin


@Composable
fun TimerSnowEffect(size: IntSize) {

    val transition = rememberInfiniteTransition()
    val snowXOffsetList = mutableListOf<Float>()
    val snowYOffsetList = mutableListOf<Float>()
    val snowSizeList = mutableListOf<Float>()
    val numberOfSnows by remember { mutableStateOf(50) }

    for (i in 0 until numberOfSnows) {
        val duration by remember { mutableStateOf((1000..2000).random()) }

        val snowSizeInitial by rememberSaveable { mutableStateOf((20..25).random().toFloat()) }
        val snowSizeTarget by rememberSaveable { mutableStateOf((0..1).random().toFloat()) }

        val snowSize by transition.animateFloat(
            initialValue = snowSizeInitial,
            targetValue = snowSizeTarget,
            animationSpec = infiniteRepeatable(
                animation = tween(duration*4, duration, easing = LinearEasing),
            )
        )
        snowSizeList.add(snowSize)

        val snowYInitial by rememberSaveable { mutableStateOf((-2000..0).random().toFloat() )}

        val snowYTarget by rememberSaveable { mutableStateOf(((size.height*0.90).toInt()..size.height).random().toFloat()) }

        val snowYOffset by transition.animateFloat(
            initialValue = snowYInitial,
            targetValue = snowYTarget,
            animationSpec = infiniteRepeatable(
                animation = tween(duration*4, duration,  easing = LinearEasing),
            )
        )
        snowYOffsetList.add(snowYOffset)

        val snowXInitial by rememberSaveable { mutableStateOf((0..size.width).random().toFloat()) }

        val snowXTarget by rememberSaveable { mutableStateOf((0..size.width).random().toFloat()) }

        val snowXOffset by transition.animateFloat(
            initialValue = snowXInitial,
            targetValue = snowXTarget,
            animationSpec = infiniteRepeatable(
                animation = tween(duration*200, duration, easing = LinearEasing),
            ),
        )
        snowXOffsetList.add(0, snowXOffset)
    }

    Box {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                for (i in 0 until numberOfSnows) {
                    val radius = snowSizeList[i] / (snowYOffsetList[i] * 0.005 + 2).toFloat()
                    drawCircle(
                        color = Color.White,
                        radius = if (radius > 25f) 25f else radius,
                        center = Offset(
                            x = snowXOffsetList[i]+sin(snowXOffsetList[i]*10),
                            y = snowYOffsetList[i],
                        )
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Black00,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                        )
                    )
                )
                .matchParentSize()
        )
    }

}