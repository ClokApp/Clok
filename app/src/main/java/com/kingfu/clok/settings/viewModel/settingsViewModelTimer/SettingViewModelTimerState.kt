package com.kingfu.clok.settings.viewModel.settingsViewModelTimer

import com.kingfu.clok.timer.feature.timerFontStyle.TimerFontStyleType
import com.kingfu.clok.timer.feature.timerProgressBarBackgroundEffects.TimerProgressBarBackgroundEffectType
import com.kingfu.clok.timer.feature.timerProgressBarStyle.TimerProgressBarStyleType
import com.kingfu.clok.timer.feature.timerScrollsHapticFeedback.TimerScrollsHapticFeedbackType

data class SettingViewModelTimerState(
    val timerProgressBarStyle: String = TimerProgressBarStyleType.DynamicColor.name,
    val timerIsCountOvertime: Boolean = true,
    var timerNotification: Float = 5f,
    var timerProgressBarBackgroundEffect: String = TimerProgressBarBackgroundEffectType.Snow.name,
    var timerScrollsFontStyle: String = TimerFontStyleType.Default.name,
    var timerTimeFontStyle: String = TimerFontStyleType.Default.name,
    var timerScrollsHapticFeedback: String = TimerScrollsHapticFeedbackType.Strong.name
)