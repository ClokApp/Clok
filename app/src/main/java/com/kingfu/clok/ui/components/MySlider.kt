package com.kingfu.clok.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySlider(
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 1f..100f,
    height: Dp = 50.dp,
    value: Float,
    onValueChange: ((sliderValue: Float) -> Unit)? = null,
    activeTrackColor: Color = MaterialTheme.colorScheme.primary,
    inactiveTrackColor: Color = MaterialTheme.colorScheme.primary,
    isEnabled: Boolean = true,
    content: (@Composable RowScope.() -> Unit)? = null,
    outerContentPadding: Dp = 16.dp,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = outerContentPadding)
            .height(height = height),
        contentAlignment = Alignment.Center
    ) {
        Slider(
            value = value,
            valueRange = valueRange,
            onValueChange = {
                if (onValueChange != null) {
                    onValueChange(it)
                }
            },
            enabled = isEnabled,
            colors = SliderDefaults.colors(
                activeTrackColor = activeTrackColor,
                inactiveTrackColor = inactiveTrackColor.copy(alpha = 0.50f),
            ),
            modifier = Modifier
                .matchParentSize()
                .clip(shape = shapes.medium)
                .scale(
                    scaleX = 1.1f,
                    scaleY = height.value / 2
                ),
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    thumbSize = DpSize.Zero
                )
            }
        )

        if (content != null) {
            Row(
                modifier = Modifier.matchParentSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = CenterVertically
            ) {
                content()
            }
        }
    }
}


@Preview
@Composable
fun CustomSliderPreview() {

    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {

        var sliderValue by remember { mutableFloatStateOf(value = 80f) }

        MySlider(
            value = sliderValue,
            height = 50.dp,
            onValueChange = {
                sliderValue = it
            }
        )
    }
}