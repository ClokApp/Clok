package com.kingfu.clok.timer.styles

import androidx.compose.ui.graphics.Color
import com.kingfu.clok.timer.timerViewModel.TimerViewModel.TimerViewModelVariable.timerTime
import com.kingfu.clok.ui.theme.Cyan50
import com.kingfu.clok.ui.theme.Red50

class TimerCyanStyle{
    fun cyanStyleListOfCyan(): Color {
        return if (timerTime > 6000) {
            Cyan50
        } else {
            Red50
        }
    }
}
