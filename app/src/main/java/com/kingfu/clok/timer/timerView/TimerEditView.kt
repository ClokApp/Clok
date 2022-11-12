package com.kingfu.clok.timer.timerView

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.ui.theme.Cyan50
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.variable.Variable
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun TimerEditView(
    vm: TimerViewModel,
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    selectedHr: Int?,
    selectedMin: Int?,
    selectedSec: Int?,
    haptic: HapticFeedback,
) {
    val configurationOrientation = LocalConfiguration.current.orientation

    LaunchedEffect(Unit){
        vm.loadTimerEnableScrollsHapticFeedback()
    }

    LaunchedEffect(selectedHr) {
        if (selectedHr != null) {
            vm.updateTimerHour(selectedHr)
            vm.saveTimerHour()
        }

        if(!lazyListStateHr.isScrollInProgress){
            lazyListStateHr.scrollToItem(Int.MAX_VALUE / 2 - 15 + vm.timerHour)
        }
    }

    LaunchedEffect(selectedMin) {
        if (selectedMin != null) {
            vm.updateTimerMinute(selectedMin)
            vm.saveTimerMinute()
        }

        if(!lazyListStateMin.isScrollInProgress){
            lazyListStateMin.scrollToItem(Int.MAX_VALUE / 2 - 3 + vm.timerMinute)
        }
    }

    LaunchedEffect(selectedSec) {
        if (selectedSec != null) {
            vm.updateTimerSecond(selectedSec)
            vm.saveTimerSecond()
        }
        if(!lazyListStateSec.isScrollInProgress) {
            lazyListStateSec.scrollToItem(Int.MAX_VALUE / 2 - 3 + vm.timerSecond)
        }
    }


    Row(
        modifier = Modifier
            .height(
                if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    280.dp
                } else {
                    200.dp
                }
            )
    ) {

        val timerScrollerFontSize =
            if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                customFontSize(textUnit = 65.sp)
            } else {
                customFontSize(textUnit = 50.sp)
            }

        val timerEndContentPadding =
            if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                PaddingValues(vertical = 90.dp)
            } else {
                PaddingValues(vertical = 50.dp)
            }

        val timerContentPadding =
            if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                PaddingValues(vertical = 75.dp)
            } else {
                PaddingValues(vertical = 50.dp)
            }

        val timerScrollerSpacing =
            if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                2.dp
            } else {
                6.dp
            }

        val timerBoxGradient =
            listOf(
                Black00,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                Black00
            )

        val labelTextSize = customFontSize(textUnit = 18.sp)

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val colorHr by animateColorAsState(
                if (lazyListStateHr.isScrollInProgress) Color.White else Color.Gray,
                animationSpec = tween(
                    durationMillis = 500,
                    delayMillis = 0,
                    easing = LinearEasing
                )
            )

            Text(
                text = "Hours",
                modifier = Modifier.padding(bottom = 5.dp),
                textAlign = TextAlign.Center,
                fontSize = labelTextSize,
                color = colorHr,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Thin,
            )

            Box {
                LazyColumn(
                    state = lazyListStateHr,
                    flingBehavior = rememberSnapperFlingBehavior(
                        lazyListStateHr,
                        endContentPadding = timerEndContentPadding.calculateTopPadding(),
                    ),
                    contentPadding = timerContentPadding,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    items(Int.MAX_VALUE) { index ->
                        val hourSelectedColor by animateColorAsState(
                            if (selectedHr == index % Variable.TIMER_HR) Cyan50 else Color.DarkGray
                        )

                        Text(
                            text = if (index % Variable.TIMER_HR < 10) "0${index % Variable.TIMER_HR}" else "${index % Variable.TIMER_HR}",
                            textAlign = TextAlign.Center,
                            fontSize = timerScrollerFontSize,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Light,
                            color = hourSelectedColor,
                        )
                    }
                }


                LaunchedEffect(selectedHr) {
                    if (lazyListStateHr.isScrollInProgress && vm.timerEnableScrollsHapticFeedback) {
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

        Spacer(modifier = Modifier.padding(horizontal = timerScrollerSpacing))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val colorMin by animateColorAsState(
                if (lazyListStateMin.isScrollInProgress) Color.White else Color.Gray,
                animationSpec = tween(
                    durationMillis = 500,
                    delayMillis = 0,
                    easing = LinearEasing
                )
            )

            Text(
                text = "Minutes",
                modifier = Modifier.padding(bottom = 5.dp),
                textAlign = TextAlign.Center,
                fontSize = labelTextSize,
                color = colorMin,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Thin,
            )
            Box {
                LazyColumn(
                    state = lazyListStateMin,
                    flingBehavior = rememberSnapperFlingBehavior(
                        lazyListStateMin,
                        endContentPadding = timerEndContentPadding.calculateTopPadding(),
                    ),
                    contentPadding = timerContentPadding,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(Int.MAX_VALUE) { index ->
                        val minuteSelectedColor by animateColorAsState(
                            if (selectedMin == index % Variable.TIMER_MIN) Cyan50 else Color.DarkGray
                        )

                        Text(
                            text = if (index % Variable.TIMER_MIN < 10) "0${index % Variable.TIMER_MIN}" else "${index % Variable.TIMER_MIN}",
                            fontSize = timerScrollerFontSize,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Light,
                            color = minuteSelectedColor,
                        )
                    }
                }

                LaunchedEffect(selectedMin) {
                    if (lazyListStateMin.isScrollInProgress && vm.timerEnableScrollsHapticFeedback) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }

                Box(
                    modifier = Modifier
                        .background(Brush.verticalGradient(colors = timerBoxGradient))
                        .matchParentSize()
                )
            }
        }

        Spacer(modifier = Modifier.padding(horizontal = timerScrollerSpacing))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val colorSec by animateColorAsState(
                if (lazyListStateSec.isScrollInProgress) Color.White else Color.Gray,
                animationSpec = tween(
                    durationMillis = 500,
                    delayMillis = 0,
                    easing = LinearEasing
                )
            )

            Text(
                text = "Seconds",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = labelTextSize,
                color = colorSec,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Thin,
            )

            Box {
                LazyColumn(
                    state = lazyListStateSec,
                    flingBehavior = rememberSnapperFlingBehavior(
                        lazyListStateSec,
                        endContentPadding = timerEndContentPadding.calculateTopPadding(),
                    ),
                    contentPadding = timerContentPadding,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    items(Int.MAX_VALUE) { index ->
                        val secondSelectedColor by animateColorAsState(
                            if (selectedSec == index % Variable.TIMER_SEC) Cyan50 else Color.DarkGray
                        )

                        Text(
                            text = if (index % Variable.TIMER_SEC < 10) "0${index % Variable.TIMER_SEC}" else "${index % Variable.TIMER_SEC}",
                            fontSize = timerScrollerFontSize,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Light,
                            color = secondSelectedColor,
                        )
                    }
                }

                LaunchedEffect(selectedSec) {
                    if (lazyListStateSec.isScrollInProgress && vm.timerEnableScrollsHapticFeedback) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
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
}