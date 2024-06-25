package com.kingfu.clok.ui.util

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isPortrait() = LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT
