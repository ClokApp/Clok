package com.kingfu.clok.timer.timerScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraLight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.timer.feature.timerFontStyle.TimerFontStyleType
import com.kingfu.clok.timer.feature.timerFontStyle.timerFontStyle
import com.kingfu.clok.timer.feature.timerScrollsHapticFeedback.TimerScrollsHapticFeedbackType
import com.kingfu.clok.timer.feature.timerScrollsHapticFeedback.timerScrollsHapticFeedback
import com.kingfu.clok.ui.theme.TextLabelSmall
import com.kingfu.clok.ui.theme.themeBackgroundColor
import com.kingfu.clok.ui.util.nonScaledSp
import com.kingfu.clok.variable.Variable.TIMER_HR
import com.kingfu.clok.variable.Variable.TIMER_MIN
import com.kingfu.clok.variable.Variable.TIMER_SEC

@Composable
fun TimerEdit(
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    haptic: HapticFeedback,
    timerHour: Int,
    timerMinute: Int,
    timerSecond: Int,
    setLoadInitialTimeToFalse: () -> Unit,
    updateTimerHour: (hour: Int) -> Unit,
    saveTimerHour: () -> Unit,
    updateTimerMinute: (minute: Int) -> Unit,
    saveTimerMinute: () -> Unit,
    updateTimerSecond: (second: Int) -> Unit,
    saveTimerSecond: () -> Unit,
    getTimerScrollsFontStyle: () -> TimerFontStyleType,
    getTimerScrollsHapticFeedback: () -> TimerScrollsHapticFeedbackType,
    theme: ThemeType
) {

    val selectedItemHr by remember { derivedStateOf { lazyListStateHr.firstVisibleItemIndex + 1 } }
    val selectedItemMin by remember { derivedStateOf { lazyListStateMin.firstVisibleItemIndex + 1 } }
    val selectedItemSec by remember { derivedStateOf { lazyListStateSec.firstVisibleItemIndex + 1 } }

    val labels = listOf(
        stringResource(id = R.string.timer_edit_screen_hours),
        stringResource(id = R.string.timer_edit_screen_minutes),
        stringResource(id = R.string.timer_edit_screen_seconds)
    )

    LaunchedEffect(key1 = true) {
        lazyListStateHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24 + timerHour)
        lazyListStateMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + timerMinute)
        lazyListStateSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + timerSecond)
        setLoadInitialTimeToFalse()
    }


    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(200.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val timerScrollerFontSize = 51.dp.value.nonScaledSp

        val timerBoxGradient =
            listOf(
                themeBackgroundColor(theme = theme),
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                themeBackgroundColor(theme = theme)
            )

        val labelTextSize = 18.dp.value.nonScaledSp


        DisplayTimerScroll(
            label = labels[0],
            lazyListState = lazyListStateHr,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemHr,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_HR,
            labels = labels,
            getTimerScrollsFontStyle = getTimerScrollsFontStyle,
            getTimerScrollsHapticFeedback = getTimerScrollsHapticFeedback,
            labelTextSize = labelTextSize,
            updateTimerHour = updateTimerHour,
            saveTimerHour = saveTimerHour,
            updateTimerMinute = updateTimerMinute,
            saveTimerMinute = saveTimerMinute,
            updateTimerSecond = updateTimerSecond,
            saveTimerSecond = saveTimerSecond
        )

        DisplayTimerScroll(
            label = labels[1],
            lazyListState = lazyListStateMin,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemMin,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_MIN,
            labels = labels,
            labelTextSize = labelTextSize,
            getTimerScrollsFontStyle = getTimerScrollsFontStyle,
            getTimerScrollsHapticFeedback = getTimerScrollsHapticFeedback,
            updateTimerHour = updateTimerHour,
            saveTimerHour = saveTimerHour,
            updateTimerMinute = updateTimerMinute,
            saveTimerMinute = saveTimerMinute,
            updateTimerSecond = updateTimerSecond,
            saveTimerSecond = saveTimerSecond
        )

        DisplayTimerScroll(
            label = labels[2],
            lazyListState = lazyListStateSec,
            timerScrollerFontSize = timerScrollerFontSize,
            selectedItem = selectedItemSec,
            haptic = haptic,
            timerBoxGradient = timerBoxGradient,
            timeUnit = TIMER_SEC,
            labels = labels,
            getTimerScrollsFontStyle = getTimerScrollsFontStyle,
            getTimerScrollsHapticFeedback = getTimerScrollsHapticFeedback,
            labelTextSize = labelTextSize,
            updateTimerHour = updateTimerHour,
            saveTimerHour = saveTimerHour,
            updateTimerMinute = updateTimerMinute,
            saveTimerMinute = saveTimerMinute,
            updateTimerSecond = updateTimerSecond,
            saveTimerSecond = saveTimerSecond
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayTimerScroll(
    label: String,
    lazyListState: LazyListState,
    timerScrollerFontSize: TextUnit,
    selectedItem: Int,
    haptic: HapticFeedback,
    timerBoxGradient: List<Color>,
    timeUnit: Int,
    labels: List<String>,
    labelTextSize: TextUnit,
    getTimerScrollsFontStyle: () -> TimerFontStyleType,
    getTimerScrollsHapticFeedback: () -> TimerScrollsHapticFeedbackType,
    updateTimerHour: (int: Int) -> Unit,
    saveTimerHour: () -> Unit,
    updateTimerMinute: (int: Int) -> Unit,
    saveTimerMinute: () -> Unit,
    updateTimerSecond: (int: Int) -> Unit,
    saveTimerSecond: () -> Unit,
) {


    Column(
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        val color by animateColorAsState(
            if (lazyListState.isScrollInProgress) {
                colorScheme.tertiary
            } else {
                if(isSystemInDarkTheme()) colorScheme.tertiaryContainer else colorScheme.primary
            },
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 0,
                easing = LinearEasing
            ),
            label = ""
        )

        TextLabelSmall(
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
                                selectedStyle = getTimerScrollsFontStyle(),
                                miter = 10f,
                                width = 2.5f,
                                join = StrokeJoin.Round,
                                cap = StrokeCap.Round
                            )
                        )
                    )

                }
            }

            LaunchedEffect(key1 = selectedItem) {
                if (lazyListState.isScrollInProgress) {
                    timerScrollsHapticFeedback(
                        timerScrollsHapticFeedbackType = getTimerScrollsHapticFeedback(),
                        haptic = haptic
                    )
                }
                when (label) {
                    labels[0] -> {
                        updateTimerHour(selectedItem % timeUnit)
                        saveTimerHour()
                    }

                    labels[1] -> {
                        updateTimerMinute(selectedItem % timeUnit)
                        saveTimerMinute()
                    }

                    labels[2] -> {
                        updateTimerSecond(selectedItem % timeUnit)
                        saveTimerSecond()
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






