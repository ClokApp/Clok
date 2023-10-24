package com.kingfu.clok.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun screenHeight() = LocalConfiguration.current.screenHeightDp.dp