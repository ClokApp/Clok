package com.kingfu.clok.settings.viewModel.settingsViewModelStopwatch

import com.kingfu.clok.stopwatch.util.fontStyle.StopwatchFontStyleType
import com.kingfu.clok.stopwatch.util.labelBackgroundEffects.StopwatchLabelBackgroundEffectType
import com.kingfu.clok.stopwatch.util.labelStyle.StopwatchLabelStyleType
import java.io.Serializable

data class SettingsViewModelStopwatchState(
    val stopwatchIsShowLabel: Boolean = true,
    val stopwatchLabelBackgroundEffects: String = StopwatchLabelBackgroundEffectType.Snow.name,
    val stopwatchRefreshRateValue: Float = 51f,
    val stopwatchLabelStyle: String = StopwatchLabelStyleType.DynamicColor.name,
    val stopwatchLabelFontStyle: String = StopwatchFontStyleType.Default.name,
    val stopwatchTimeFontStyle: String =  StopwatchFontStyleType.Default.name,
    val stopwatchLapTimeFontStyle: String = StopwatchFontStyleType.Default.name
) : Serializable