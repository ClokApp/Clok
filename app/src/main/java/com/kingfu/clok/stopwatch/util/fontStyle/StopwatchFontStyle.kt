package com.kingfu.clok.stopwatch.util.fontStyle

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun stopwatchFontStyle(
    selectedFontStyle: StopwatchFontStyleType,
    miter: Float? = 0f,
    width: Float? = 4.0f,
    join: StrokeJoin? = StrokeJoin.Round,
    cap: StrokeCap? = StrokeCap.Round
): DrawStyle? {

    return when (selectedFontStyle) {
        StopwatchFontStyleType.InnerStroke -> Stroke(
            miter = miter!!,
            width = width!!,
            join = join!! ,
            cap = cap!!
        )
        StopwatchFontStyleType.Default -> null
    }
}

