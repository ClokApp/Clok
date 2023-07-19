package com.kingfu.clok.stopwatch.backgroundEffects

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.IntSize
import com.kingfu.clok.ui.theme.Black00
import kotlin.math.sin


@Composable
fun StopwatchSnowEffect(size: IntSize) {

    val transition = rememberInfiniteTransition()
    val snowXOffsetList = mutableListOf<Float>()
    val snowYOffsetList = mutableListOf<Float>()
    val snowSizeList = mutableListOf<Float>()
    val numberOfSnows by remember { mutableIntStateOf(value = 50) }

    for (i in 0 until numberOfSnows) {
        val duration by rememberSaveable { mutableIntStateOf(value = (2000..5000).random()) }

        val snowSizeInitial by rememberSaveable {
            mutableFloatStateOf(
                value = (20..25).random().toFloat()
            )
        }
        val snowSizeTarget by rememberSaveable {
            mutableFloatStateOf(
                value = (0..1).random().toFloat()
            )
        }

        val snowSize by transition.animateFloat(
            initialValue = snowSizeInitial,
            targetValue = snowSizeTarget,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration,
                    delayMillis = duration / 8,
                    easing = LinearEasing
                ),
            )
        )
        snowSizeList.add(element = snowSize)

        val snowYInitial by rememberSaveable {
            mutableFloatStateOf(
                value = (-250..-10).random().toFloat()
            )
        }

        val snowYTarget by rememberSaveable {
            mutableFloatStateOf(
                value = ((size.height * 0.40).toInt()..(size.height * 0.50).toInt()).random()
                    .toFloat()
            )
        }

        val snowYOffset by transition.animateFloat(
            initialValue = snowYInitial,
            targetValue = snowYTarget,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration,
                    delayMillis = duration / 8,
                    easing = LinearEasing
                ),
            )
        )

        snowYOffsetList.add(snowYOffset)

        val snowXInitial by rememberSaveable {
            mutableFloatStateOf(value = (-size.width..size.width * 2).random().toFloat())
        }

        val snowXTarget by rememberSaveable { mutableFloatStateOf(value = snowXInitial) }

        val snowXOffset by transition.animateFloat(
            initialValue = snowXInitial,
            targetValue = snowXTarget,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration,
                    delayMillis = duration / 8,
                    easing = LinearEasing
                ),
            ),
        )

        snowXOffsetList.add(index = 0, element = snowXOffset)
    }

    Box {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds(),
            onDraw = {
                for (i in 0 until numberOfSnows) {
                    val radius = snowSizeList[i] / (snowYOffsetList[i] * 0.005 + 3).toFloat()
                    drawCircle(
                        color = White,
                        radius = if (radius > 25f) 25f else radius,
                        center = Offset(
                            x = snowXOffsetList[i] + 3 * sin(snowSizeList[i]),
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
                        0.1f to Black00,
                        1f to Transparent,
                        startY = 0f,
                        endY = 25f
                    )
                )
                .matchParentSize()
        )
    }
}



















