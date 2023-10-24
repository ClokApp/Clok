package com.kingfu.clok.ui.theme

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers

@Preview(name = "Red", wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE)
@Preview(name = "Blue", wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Preview(name = "Green", wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Preview(name = "Yellow", wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE)
annotation class PreviewDynamicColors

@Preview(name = "85%", fontScale = 0.85f)
@Preview(name = "100%", fontScale = 1.0f)
@Preview(name = "115%", fontScale = 1.15f)
@Preview(name = "130%", fontScale = 1.3f)
@Preview(name = "150%", fontScale = 1.5f)
@Preview(name = "180%", fontScale = 1.8f)
@Preview(name = "200%", fontScale = 2f)
annotation class PreviewFontScale