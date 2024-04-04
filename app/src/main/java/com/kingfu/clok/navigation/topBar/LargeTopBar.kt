package com.kingfu.clok.navigation.topBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextHeadLineLarge
import com.kingfu.clok.ui.theme.themeBackgroundColor
import kotlin.math.abs
import kotlin.math.sin


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LargeTopBar(
    exitUntilCollapsed: TopAppBarScrollBehavior,
    currentRoute: String?,
    navigateUp: () -> Unit,
    theme: ThemeType
) {
    val exitUntilCollapsedFraction = exitUntilCollapsed.state.collapsedFraction

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


    LargeTopAppBar(
        modifier = Modifier.offset(
            y = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
        ),
        windowInsets = WindowInsets(top = 0),
        scrollBehavior = exitUntilCollapsed,
        title = {
            TextHeadLineLarge(
                text = currentScreen,
                fontSize = if (exitUntilCollapsedFraction <= 0.545) {
                    titleFontSize * 0.90
                } else {
                    titleFontSize * 1.10
                },
                maxLines = 1,
                color = colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = if (exitUntilCollapsedFraction <= 0.5f) (-7.5).dp else 0.dp)
                    .basicMarquee(iterations = Int.MAX_VALUE)
                    .alpha(alpha = if (exitUntilCollapsedFraction <= 0.545) 1f else 0f),
                textAlign = if (exitUntilCollapsedFraction <= 0.545) {
                    TextAlign.Center
                } else {
                    TextAlign.Start
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Transparent),
        navigationIcon = {
            Row(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(
                        color = if (exitUntilCollapsedFraction <= 0.545) {
                            Transparent
                        } else {
                            themeBackgroundColor(theme = theme)
                        }
                    ),
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
                        .wrapContentHeight(align = Bottom),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (exitUntilCollapsedFraction <= 0.545) {
                            Transparent
                        } else {
                            themeBackgroundColor(theme = theme)
                        }
                    )
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
@Preview(showBackground = true)
@Composable
fun LargeTopBarPreview() {
    val theme = ThemeType.Light
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        Column(modifier = Modifier.background(color = themeBackgroundColor(theme = theme))) {
            LargeTopBar(
                exitUntilCollapsed = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
                currentRoute = Screen.BugReport.route,
                navigateUp = {},
                theme = theme
            )
        }
    }
}


