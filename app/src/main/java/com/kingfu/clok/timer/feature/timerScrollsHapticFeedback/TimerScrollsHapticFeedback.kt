package com.kingfu.clok.timer.feature.timerScrollsHapticFeedback

import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType


fun timerScrollsHapticFeedback(
    timerScrollsHapticFeedbackType: TimerScrollsHapticFeedbackType,
    haptic: HapticFeedback
) {

    when (timerScrollsHapticFeedbackType) {
        TimerScrollsHapticFeedbackType.Strong -> {
            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
        }

        TimerScrollsHapticFeedbackType.Weak -> {
            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.TextHandleMove)
        }

        TimerScrollsHapticFeedbackType.Off -> {}

    }
}
