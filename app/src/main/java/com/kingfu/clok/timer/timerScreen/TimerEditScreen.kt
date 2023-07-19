package com.kingfu.clok.timer.timerScreen

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.TextHandleMove
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraLight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.timer.timerFontStyle.timerFontStyle
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.nonScaledSp
import com.kingfu.clok.variable.Variable.TIMER_HR
import com.kingfu.clok.variable.Variable.TIMER_MIN
import com.kingfu.clok.variable.Variable.TIMER_SEC

@Composable
fun TimerEditScreen(
    vm: TimerViewModel,
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    haptic: HapticFeedback,
    configurationOrientation: Int,
    settingsViewModelTimer: SettingsViewModelTimer,
) {

    val selectedItemHr by remember { derivedStateOf { lazyListStateHr.firstVisibleItemIndex + 1 } }
    val selectedItemMin by remember { derivedStateOf { lazyListStateMin.firstVisibleItemIndex + 1 } }
    val selectedItemSec by remember { derivedStateOf { lazyListStateSec.firstVisibleItemIndex + 1 } }

    val labels = remember { mutableStateListOf("Hours", "Minutes", "Seconds") }

    LaunchedEffect(key1 = true) {
        lazyListStateHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24 + vm.timerHour)
        lazyListStateMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + vm.timerMinute)
        lazyListStateSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + vm.timerSecond,)
        vm.setLoadInitialTimeToFalse()
    }


    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(
                if (configurationOrientation == ORIENTATION_PORTRAIT) {
                    200.dp
                } else {
                    150.dp
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val timerScrollerFontSize =
            if (configurationOrientation == ORIENTATION_PORTRAIT) {
                51.nonScaledSp
            } else {
                38.5.nonScaledSp
            }

        val timerBoxGradient =
            listOf(
                Black00,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                Black00
            )

        val labelTextSize =
            if (configurationOrientation == ORIENTATION_PORTRAIT) {
                18.nonScaledSp
            } else {
                14.nonScaledSp
            }

        DisplayTimerScroll(
            label = labels[0],
            lazyListState = lazyListStateHr,
            labelTextSize = labelTextSize,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemHr,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_HR,
            vm = vm,
            labels = labels,
            settingsViewModelTimer = settingsViewModelTimer
        )

        DisplayTimerScroll(
            label = labels[1],
            lazyListState = lazyListStateMin,
            labelTextSize = labelTextSize,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemMin,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_MIN,
            vm = vm,
            labels = labels,
            settingsViewModelTimer = settingsViewModelTimer
        )

        DisplayTimerScroll(
            label = labels[2],
            lazyListState = lazyListStateSec,
            labelTextSize = labelTextSize,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemSec,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_SEC,
            vm = vm,
            labels = labels,
            settingsViewModelTimer = settingsViewModelTimer,
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayTimerScroll(
    label: String,
    lazyListState: LazyListState,
    labelTextSize: TextUnit,
    timerScrollerFontSize: TextUnit,
    selectedItem: Int,
    haptic: HapticFeedback,
    timerBoxGradient: List<Color>,
    timeUnit: Int,
    vm: TimerViewModel,
    labels: List<String>,
    settingsViewModelTimer: SettingsViewModelTimer,
) {

    Column(
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        val color by animateColorAsState(
            if (lazyListState.isScrollInProgress) {
                colorScheme.tertiary
            } else {
                colorScheme.tertiaryContainer
            },
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 0,
                easing = LinearEasing
            )
        )

        Text(
            text = label,
            textAlign = TextAlign.Center,
            fontSize = labelTextSize,
            color = color,
            fontWeight = Thin,
            style = TextStyle()
        )

        Box {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.padding(horizontal = 10.dp),
                flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
            ) {
                items(count = Int.MAX_VALUE) { index ->
                    Text(
                        text = if (index % timeUnit < 10) "0${index % timeUnit}" else "${index % timeUnit}",
                        fontSize = timerScrollerFontSize,
                        fontWeight = if (selectedItem == index) Light else ExtraLight,
                        color = if (selectedItem == index) {
                            colorScheme.primary
                        } else {
                            colorScheme.secondaryContainer
                        },
                        style = TextStyle(
                            drawStyle = timerFontStyle(
                                string1 = settingsViewModelTimer.timerScrollsFontStyle,
                                string2 = settingsViewModelTimer.timerFontStyleRadioOptions.elementAt(
                                    index = 1
                                ),
                                minter = 10f,
                                width = 5f,
                                join = StrokeJoin.Round,
                                cap = StrokeCap.Round
                            )
                        )
                    )

                }
            }

            LaunchedEffect(key1 = selectedItem) {
                if (lazyListState.isScrollInProgress && settingsViewModelTimer.timerEnableScrollsHapticFeedback) {
                    when (settingsViewModelTimer.timerScrollsHapticFeedback) {
                        "Strong" -> haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                        "Weak" -> haptic.performHapticFeedback(hapticFeedbackType = TextHandleMove)
                    }
                }
                when (label) {
                    labels[0] -> {
                        vm.updateTimerHour(hour = selectedItem % timeUnit)
                        vm.saveTimerHour()
                    }

                    labels[1] -> {
                        vm.updateTimerMinute(minute = selectedItem % timeUnit)
                        vm.saveTimerMinute()
                    }

                    labels[2] -> {
                        vm.updateTimerSecond(second = selectedItem % timeUnit)
                        vm.saveTimerSecond()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(brush = verticalGradient(colors = timerBoxGradient))
                    .matchParentSize()
            )
        }
    }
}