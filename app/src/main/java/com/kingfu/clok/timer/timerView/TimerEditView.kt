package com.kingfu.clok.timer.timerView

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerEditView(
    vm: TimerViewModel,
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    haptic: HapticFeedback,
    configurationOrientation: Int,
    settingsViewModelTimer: SettingsViewModelTimer
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val layoutInfoHr = remember(lazyListStateHr) { SnapLayoutInfoProvider(lazyListStateHr) }
    val layoutInfoMin = remember(lazyListStateMin) { SnapLayoutInfoProvider(lazyListStateMin) }
    val layoutInfoSec = remember(lazyListStateSec) { SnapLayoutInfoProvider(lazyListStateSec) }

    val selectedHr = rememberSnapFlingBehavior(snapLayoutInfoProvider = layoutInfoHr)
    val selectedMin = rememberSnapFlingBehavior(snapLayoutInfoProvider = layoutInfoMin)
    val selectedSec = rememberSnapFlingBehavior(snapLayoutInfoProvider = layoutInfoSec)

    val selectedItemHr by remember(lazyListStateHr) { derivedStateOf { lazyListStateHr.firstVisibleItemIndex + 1 } }
    val selectedItemMin by remember(lazyListStateMin) { derivedStateOf { lazyListStateMin.firstVisibleItemIndex + 1 } }
    val selectedItemSec by remember(lazyListStateSec) { derivedStateOf { lazyListStateSec.firstVisibleItemIndex + 1 } }

    var loadInitialTime by rememberSaveable { mutableStateOf(value = true) }

    val labels = remember { mutableStateListOf("Hours", "Minutes", "Seconds") }

    LaunchedEffect(Unit) {
        if (loadInitialTime && vm.timerIsEditState) {
            lazyListStateHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24 + vm.timerHour)
            lazyListStateMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + vm.timerMinute)
            lazyListStateSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + vm.timerSecond)
            loadInitialTime = false
        }
    }


    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(
                if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    screenHeight / 4f
                } else {
                    screenHeight / 2.37f
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val timerScrollerFontSize =
            if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                (screenHeight.value * 0.065).toInt().nonScaledSp
            } else {
                (screenHeight.value * 0.11).toInt().nonScaledSp
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
            if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                18.nonScaledSp
            } else {
                14.nonScaledSp
            }

        DisplayTimerScroll(
            label = labels[0],
            lazyListState = lazyListStateHr,
            labelTextSize = labelTextSize,
            flingBehavior = selectedHr,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemHr,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_HR,
            vm = vm,
            labels = labels,
            loadInitialTime = loadInitialTime,
            settingsViewModelTimer = settingsViewModelTimer,
        )

        DisplayTimerScroll(
            label = labels[1],
            lazyListState = lazyListStateMin,
            labelTextSize = labelTextSize,
            flingBehavior = selectedMin,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemMin,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_MIN,
            vm = vm,
            labels = labels,
            loadInitialTime = loadInitialTime,
            settingsViewModelTimer = settingsViewModelTimer,
        )

        DisplayTimerScroll(
            label = labels[2],
            lazyListState = lazyListStateSec,
            labelTextSize = labelTextSize,
            flingBehavior = selectedSec,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemSec,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_SEC,
            vm = vm,
            labels = labels,
            loadInitialTime = loadInitialTime,
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
    flingBehavior: SnapFlingBehavior,
    timerScrollerFontSize: TextUnit,
    selectedItem: Int,
    haptic: HapticFeedback,
    timerBoxGradient: List<Color>,
    timeUnit: Int,
    vm: TimerViewModel,
    labels: List<String>,
    loadInitialTime: Boolean,
    settingsViewModelTimer: SettingsViewModelTimer,
) {

    var currentIndex by rememberSaveable { mutableIntStateOf(value = 0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val color by animateColorAsState(
            if (lazyListState.isScrollInProgress) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiaryContainer,
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
            fontWeight = FontWeight.Thin,
            style = TextStyle()
        )

        Box {

            LazyColumn(
                state = lazyListState,
                modifier = Modifier.padding(horizontal = 10.dp),
                flingBehavior = flingBehavior,
            ) {
                items(count = Int.MAX_VALUE) { index ->
                    currentIndex = index
                    Text(
                        text = if (index % timeUnit < 10) "0${index % timeUnit}" else "${index % timeUnit}",
                        fontSize = timerScrollerFontSize,
                        fontWeight = if (selectedItem == index) FontWeight.Light else FontWeight.ExtraLight,
                        color = if (selectedItem == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
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

            LaunchedEffect(selectedItem) {
                if (lazyListState.isScrollInProgress && settingsViewModelTimer.timerEnableScrollsHapticFeedback) {
                    when (settingsViewModelTimer.timerScrollsHapticFeedback) {
                        "Strong" -> haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        "Weak" -> haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.TextHandleMove)
                    }
                }
                when (label) {
                    labels[0] -> {
                        vm.updateTimerHour(selectedItem % timeUnit)
                        vm.saveTimerHour()
                    }

                    labels[1] -> {
                        vm.updateTimerMinute(selectedItem % timeUnit)
                        vm.saveTimerMinute()
                    }

                    labels[2] -> {
                        vm.updateTimerSecond(selectedItem % timeUnit)
                        vm.saveTimerSecond()
                    }
                }
            }

            LaunchedEffect(lazyListState.isScrollInProgress) {
                if (!lazyListState.isScrollInProgress && !loadInitialTime) {
                    when (label) {
                        labels[0] -> {
                            lazyListState.scrollToItem(index = Int.MAX_VALUE / 2 - 24 + vm.timerHour)
                        }

                        labels[1] -> {
                            lazyListState.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + vm.timerMinute)
                        }

                        labels[2] -> {
                            lazyListState.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + vm.timerSecond)
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(brush = Brush.verticalGradient(colors = timerBoxGradient))
                    .matchParentSize()
            )
        }
    }
}