package com.kingfu.clok.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

fun Density.customFontSize(textUnit: TextUnit): TextUnit {
    return textUnit.value.dp.toSp()
}

@Composable
fun customFontSize(textUnit: TextUnit): TextUnit {
    return LocalDensity.current.customFontSize(textUnit)
}