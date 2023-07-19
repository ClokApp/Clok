package com.kingfu.clok.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp


val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

val Double.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp