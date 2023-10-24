package com.kingfu.clok.navigation.topBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.themeBackgroundColor
import com.kingfu.clok.ui.theme.typography
import kotlin.math.abs
import kotlin.math.sin


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CustomTopBar(
    navigateUp: () -> Unit,
    pinned: TopAppBarScrollBehavior,
    theme: ThemeType,
    exitUntilCollapsedFraction: Float,
    currentRoute: String?,
) {

    val titleFontSize = ((1 - sin(
        x = abs(exitUntilCollapsedFraction) * 2.7
    )) * typography.headlineLarge.fontSize.value).sp

    val currentScreen = when (currentRoute) {
        Screen.Settings.route -> {
            stringResource(id = Screen.Settings.nameId)
        }

        Screen.SettingsStopwatchLabelStyles.route -> {
            stringResource(id = Screen.SettingsStopwatchLabelStyles.nameId)
        }

        Screen.SettingsTimerProgressBarStyles.route -> {
            stringResource(id = Screen.SettingsTimerProgressBarStyles.nameId)
        }

        Screen.SettingsStopwatchLabelBackgroundEffects.route -> {
            stringResource(id = Screen.SettingsStopwatchLabelBackgroundEffects.nameId)
        }

        Screen.SettingsTimerProgressBarBackgroundEffects.route -> {
            stringResource(id = Screen.SettingsTimerProgressBarBackgroundEffects.nameId)
        }

        Screen.BugReport.route -> {
            stringResource(id = Screen.BugReport.nameId)
        }

        Screen.SettingsTimerFontStyles.route -> {
            stringResource(id = Screen.SettingsTimerFontStyles.nameId)
        }

        Screen.SettingsTimerTimeFontStyle.route -> {
            stringResource(id = Screen.SettingsTimerTimeFontStyle.nameId)
        }

        Screen.SettingsTimerScrollsFontStyle.route -> {
            stringResource(id = Screen.SettingsTimerScrollsFontStyle.nameId)
        }

        Screen.SettingsStopwatchFontStyles.route -> {
            stringResource(id = Screen.SettingsStopwatchFontStyles.nameId)
        }

        Screen.SettingsStopwatchLabelFontStyle.route -> {
            stringResource(id = Screen.SettingsStopwatchLabelFontStyle.nameId)
        }

        Screen.SettingsStopwatchTimeFontStyle.route -> {
            stringResource(id = Screen.SettingsStopwatchTimeFontStyle.nameId)
        }

        Screen.SettingsStopwatchLapTimeFontStyle.route -> {
            stringResource(id = Screen.SettingsStopwatchLapTimeFontStyle.nameId)
        }

        Screen.SettingsTimerScrollsHapticFeedback.route -> {
            stringResource(id = Screen.SettingsTimerScrollsHapticFeedback.nameId)
        }

        Screen.SettingsTheme.route -> {
            stringResource(id = Screen.SettingsTheme.nameId)
        }

        else -> {
            ""
        }
    }


    TopAppBar(
        scrollBehavior = pinned,
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Transparent),
        navigationIcon = {
            Row(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(
                        color = if (exitUntilCollapsedFraction <= 0.545) {
                            Transparent
                        } else {
                            themeBackgroundColor(theme = theme).copy(0.95f)
                        }
                    )
//                    .border(
//                        width = 0.5.dp,
//                        shape = RoundedCornerShape(percent = 50),
//                        brush = Brush.linearGradient(
//                            listOf(
//                                colorScheme.primary,
//                                colorScheme.primary
////                                colorScheme.tertiary
//                            )
//                        )
//                    )
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    enabled = currentRoute != Screen.Stopwatch.route && currentRoute != Screen.Timer.route,
                    onClick = { navigateUp() },
                    modifier = Modifier
                        .alpha(
                            alpha = if (
                                currentRoute != Screen.Stopwatch.route &&
                                currentRoute != Screen.Timer.route
                            ) 1f else 0f
                        )
                        .wrapContentHeight(align = Alignment.Bottom),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        tint = colorScheme.primary
                    )
                }

                TextBodyLarge(
                    text = currentScreen,
                    maxLines = 1,
                    color = colorScheme.primary,
                    modifier = Modifier
                        .alpha(alpha = if (exitUntilCollapsedFraction <= 0.545) 0f else 1f)
                        .basicMarquee(iterations = Int.MAX_VALUE),
                    fontSize = if (exitUntilCollapsedFraction <= 0.545) {
                        titleFontSize * 0.90
                    } else {
                        titleFontSize * 1.10
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CustomTopBarPreview() {
    val theme = ThemeType.Light
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        Column(modifier = Modifier.background(color = themeBackgroundColor(theme = theme))) {
            CustomTopBar(
                exitUntilCollapsedFraction = 1f,
                currentRoute = Screen.SettingsTheme.route,
                navigateUp = {},
                pinned = TopAppBarDefaults.pinnedScrollBehavior(),
                theme = theme,
            )
        }
    }
}


