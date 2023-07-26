package com.kingfu.clok.components.topBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.settingsStopwatchSelectedFontStyle
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyle
import kotlin.math.abs
import kotlin.math.sin


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LargeTopBar(
    topBarScrollBehavior: TopAppBarScrollBehavior,
    currentDestination: NavDestination?,
    navigateUp: () -> Unit,
) {
    val currentRoute by rememberSaveable { mutableStateOf(value = currentDestination?.route) }

    val titleFontSize =
        ((1 - sin(x = abs(topBarScrollBehavior.state.collapsedFraction) * 2.6)) * 40).sp


    LargeTopAppBar(
        scrollBehavior = topBarScrollBehavior,
        title = {
            Text(
                text = when (currentRoute) {
                    Screens.Settings.route -> {
                        Screens.Settings.name
                    }

                    Screens.SettingsStopwatchLabelStyles.route -> {
                        Screens.SettingsStopwatchLabelStyles.name
                    }

                    Screens.SettingsTimerProgressBarStyles.route -> {
                        Screens.SettingsTimerProgressBarStyles.name
                    }

                    Screens.SettingsStopwatchLabelBackgroundEffects.route -> {
                        Screens.SettingsStopwatchLabelBackgroundEffects.name
                    }

                    Screens.SettingsTimerProgressBarBackgroundEffects.route -> {
                        Screens.SettingsTimerProgressBarBackgroundEffects.name
                    }

                    Screens.BugReport.route -> {
                        Screens.BugReport.name
                    }

                    Screens.SettingsTimerFontStyles.route -> {
                        Screens.SettingsTimerFontStyles.name
                    }

                    Screens.SettingsTimerSelectedFontStyle.route -> {
                        settingsTimerSelectedFontStyle
                    }

                    Screens.SettingsStopwatchFontStyles.route -> {
                        Screens.SettingsStopwatchFontStyles.name
                    }

                    Screens.SettingsStopwatchSelectedFontStyle.route -> {
                        settingsStopwatchSelectedFontStyle
                    }

                    Screens.SettingsTimerScrollsHapticFeedback.route -> {
                        Screens.SettingsTimerScrollsHapticFeedback.name
                    }

                    else -> {
                        ""
                    }
                },
                fontSize =
                if (topBarScrollBehavior.state.collapsedFraction <= 0.545) {
                    titleFontSize * 0.90
                } else {
                    titleFontSize * 1.10
                },
                maxLines = 1,
                color = colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = if (topBarScrollBehavior.state.collapsedFraction <= 0.5f) (-7.5).dp else 0.dp)
                    .basicMarquee(iterations = Int.MAX_VALUE),
                textAlign =
                if (topBarScrollBehavior.state.collapsedFraction <= 0.545) {
                    TextAlign.Center
                } else {
                    TextAlign.Start
                },
                style = TextStyle()
            )

        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Black00),
        navigationIcon =
        {
            IconButton(
                enabled = currentRoute != Screens.Stopwatch.route && currentRoute != Screens.Timer.route,
                onClick = { navigateUp() },
                modifier = Modifier
                    .alpha(
                        if (
                            currentRoute != Screens.Stopwatch.route &&
                            currentRoute != Screens.Timer.route
                        ) 1f else 0f
                    )
                    .wrapContentHeight(align = Bottom),
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = colorScheme.primary
                )
            }
        }
    )
}


