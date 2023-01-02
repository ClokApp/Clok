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
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerEnableScrollsHapticFeedback
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.ui.theme.Cyan50
import com.kingfu.clok.util.customFontSize
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
    configurationOrientation: Int
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp


    val layoutInfoHr = remember(lazyListStateHr) { SnapLayoutInfoProvider(lazyListStateHr)}
    val layoutInfoMin = remember(lazyListStateMin) { SnapLayoutInfoProvider(lazyListStateMin) }
    val layoutInfoSec = remember(lazyListStateSec) { SnapLayoutInfoProvider(lazyListStateSec) }

    val selectedHr = rememberSnapFlingBehavior(snapLayoutInfoProvider = layoutInfoHr)
    val selectedMin = rememberSnapFlingBehavior(snapLayoutInfoProvider = layoutInfoMin)
    val selectedSec = rememberSnapFlingBehavior(snapLayoutInfoProvider = layoutInfoSec)

    val selectedItemHr by remember { derivedStateOf { lazyListStateHr.firstVisibleItemIndex +1 } }
    val selectedItemMin by remember { derivedStateOf { lazyListStateMin.firstVisibleItemIndex + 1 } }
    val selectedItemSec by remember { derivedStateOf { lazyListStateSec.firstVisibleItemIndex + 1 } }


    LaunchedEffect(selectedItemHr) {
        vm.updateTimerHour(selectedItemHr % TIMER_HR)
        vm.saveTimerHour()
    }

    LaunchedEffect(selectedItemMin) {
        vm.updateTimerMinute(selectedItemMin % TIMER_MIN)
        vm.saveTimerMinute()
    }

    LaunchedEffect(selectedItemSec) {
        vm.updateTimerSecond(selectedItemSec % TIMER_SEC)
        vm.saveTimerSecond()
    }

    LaunchedEffect(lazyListStateHr.isScrollInProgress) {
        if (!lazyListStateHr.isScrollInProgress) {
            lazyListStateHr.scrollToItem(Int.MAX_VALUE / 2 - 24 + vm.timerHour)
        }
    }

    LaunchedEffect(lazyListStateMin.isScrollInProgress) {
        if (!lazyListStateMin.isScrollInProgress) {
            lazyListStateMin.scrollToItem(Int.MAX_VALUE / 2 - 4 + vm.timerMinute)
        }
    }

    LaunchedEffect(lazyListStateSec.isScrollInProgress) {
        if (!lazyListStateSec.isScrollInProgress) {
            lazyListStateSec.scrollToItem(Int.MAX_VALUE / 2 - 4 + vm.timerSecond)
        }
    }


    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(
                if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    screenHeight / 3.3f
                } else {
                    screenHeight / 1.9f
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val timerScrollerFontSize =
            if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                customFontSize(textUnit = (screenHeight.value * 0.070).sp)
            } else {
                customFontSize(textUnit = (screenHeight.value * 0.12).sp)

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
                customFontSize(textUnit = 18.sp)
            } else {
                customFontSize(textUnit = 14.sp)

            }

        DisplayTimerScroll(
            label = "Hours",
            lazyListState = lazyListStateHr,
            labelTextSize = labelTextSize,
            flingBehavior = selectedHr,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemHr,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_HR,
        )

        DisplayTimerScroll(
            label = "Minutes",
            lazyListState = lazyListStateMin,
            labelTextSize = labelTextSize,
            flingBehavior = selectedMin,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemMin,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_MIN,
        )

        DisplayTimerScroll(
            label = "Seconds",
            lazyListState = lazyListStateSec,
            labelTextSize = labelTextSize,
            flingBehavior = selectedSec,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemSec,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_SEC,
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
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier

    ) {
        val color by animateColorAsState(
            if (lazyListState.isScrollInProgress) Color.White else Color.Gray,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 0,
//                easing = EaseInOut
                easing = LinearEasing

            )
        )

        Text(
            text = label,
            textAlign = TextAlign.Center,
            fontSize = labelTextSize,
            color = color,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Thin,

            )

        Box {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                flingBehavior = flingBehavior,


                ) {
                items(Int.MAX_VALUE) { index ->
                    Text(
                        text = if (index % timeUnit < 10) "0${index % timeUnit}" else "${index % timeUnit}",
                        textAlign = TextAlign.Center,
                        fontSize = timerScrollerFontSize,
                        fontFamily = FontFamily.Default,
                        fontWeight = if (selectedItem == index) FontWeight.Light else FontWeight.ExtraLight,
                        color = if (selectedItem == index) Cyan50 else Color.DarkGray,
                    )
                }
            }

            LaunchedEffect(selectedItem) {
                if (lazyListState.isScrollInProgress && timerEnableScrollsHapticFeedback) {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                }
            }

            Box(
                modifier = Modifier
                    .background(Brush.verticalGradient(colors = timerBoxGradient))
                    .matchParentSize()
            )
        }
    }
}