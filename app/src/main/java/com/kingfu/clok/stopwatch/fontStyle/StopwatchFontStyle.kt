package com.kingfu.clok.stopwatch.fontStyle

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke

fun stopwatchFontStyle(
    string1: String,
    string2: String,
    minter: Float?,
    width: Float?,
    join: StrokeJoin?,
    cap: StrokeCap?
): DrawStyle? {
    if (string1 == string2) {
        return Stroke(
            miter = minter ?: 0f,
            width = width ?: 4.0f,
            join = join ?: StrokeJoin.Miter,
            cap = cap ?: StrokeCap.Butt
        )
    }
    return null
}