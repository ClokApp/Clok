package com.kingfu.clok.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

val Float.nonScaledSp: TextUnit
    @Composable
    get() {
        val value = this
        return LocalDensity.current.run { value.dp.toPx().toSp() }
    }
