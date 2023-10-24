package com.kingfu.clok.ui.util

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.IntSize
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import kotlin.math.sin


@Composable
fun SnowEffect(
    modifier: Modifier = Modifier,
    size: IntSize,
    startHeightMultiplier: Float = 1f,
    endHeightMultiplier: Float = 1f,
    startWidthMultiplier: Float = 1f,
    endWidthMultiplier: Float = 1f,
    theme: ThemeType,
    durationRange: IntRange = 2000..5000,
    numberOfSnow: Int = 50
) {
    val transition = rememberInfiniteTransition(label = "")
    val snowXOffsetList = mutableListOf<Float>()
    val snowYOffsetList = mutableListOf<Float>()
    val snowSizeList = mutableListOf<Float>()
//    val numberOfSnows by remember { mutableIntStateOf(value = numberOfSnow) }

    for (i in 0 until numberOfSnow) {
        val duration by rememberSaveable { mutableIntStateOf(value = (durationRange).random()) }

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
            ),
            label = ""
        )
        snowSizeList.add(element = snowSize)

        val snowYInitial by rememberSaveable {
            mutableFloatStateOf(
                value = (-250..-10).random().toFloat()
            )
        }

        val snowYTarget by rememberSaveable {
            mutableFloatStateOf(
                value = ((size.height  * startHeightMultiplier).toInt()..(size.height * endHeightMultiplier).toInt()).random().toFloat()
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
            ),
            label = ""
        )

        snowYOffsetList.add(snowYOffset)

        val snowXInitial by rememberSaveable {
            mutableFloatStateOf(
                value = ((-size.width * startWidthMultiplier).toInt()..(size.width * endWidthMultiplier).toInt()).random().toFloat()
            )
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
            label = "",
        )

        snowXOffsetList.add(index = 0, element = snowXOffset)
    }

    Box(modifier = modifier) {
        val snowColor = when(theme){
            ThemeType.Light -> colorScheme.tertiary
            ThemeType.Dark -> White
            ThemeType.System -> if(isSystemInDarkTheme()) White else colorScheme.tertiary
        }
        val background = when(theme){
            ThemeType.Light -> White
            ThemeType.Dark -> Black
            ThemeType.System -> if(isSystemInDarkTheme()) Black else White
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds(),
            onDraw = {
                for (i in 0 until numberOfSnow) {
                    val radius = snowSizeList[i] / (snowYOffsetList[i] * 0.005 + 3).toFloat()
                    drawCircle(
                        color = snowColor,
                        radius = if (radius > 25f) 25f else radius,
                        center = Offset(
                            x = snowXOffsetList[i] + 3 * sin(snowSizeList[i]),
                            y = snowYOffsetList[i]
                        )
                    )
                }
            }

        )
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        0.1f to background,
                        1f to Transparent,
                        startY = 0f,
                        endY = 25f
                    )
                )
                .matchParentSize()
        )
    }
}