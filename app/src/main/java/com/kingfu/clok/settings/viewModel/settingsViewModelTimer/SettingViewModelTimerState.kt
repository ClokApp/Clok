package com.kingfu.clok.settings.viewModel.settingsViewModelTimer

import com.kingfu.clok.timer.util.timerFontStyle.TimerFontStyleType
import com.kingfu.clok.timer.util.timerProgressBarBackgroundEffects.TimerProgressBarBackgroundEffectType
import com.kingfu.clok.timer.util.timerProgressBarStyle.TimerProgressBarStyleType
import com.kingfu.clok.timer.util.timerScrollsHapticFeedback.TimerScrollsHapticFeedbackType

data class SettingViewModelTimerState(
    val timerProgressBarStyle: String = TimerProgressBarStyleType.DynamicColor.name,
    val timerIsCountOvertime: Boolean = true,
    var timerNotification: Float = 5f,
    var timerProgressBarBackgroundEffect: String = TimerProgressBarBackgroundEffectType.Snow.name,
    var timerScrollsFontStyle: String = TimerFontStyleType.Default.name,
    var timerTimeFontStyle: String = TimerFontStyleType.Default.name,
    var timerScrollsHapticFeedback: String = TimerScrollsHapticFeedbackType.Strong.name
)