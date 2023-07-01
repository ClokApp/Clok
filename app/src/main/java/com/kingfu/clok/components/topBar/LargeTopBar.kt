package com.kingfu.clok.components.topBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.settingsStopwatchSelectedFontStyle
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyle
import kotlin.math.abs
import kotlin.math.sin


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeTopBar(
    navController: NavController,
    topBarScrollBehavior: TopAppBarScrollBehavior,
) {
    val currentRoute by rememberSaveable{ mutableStateOf(navController.currentDestination?.route) }

    val titleFontSize = ((1 - sin(x = abs(topBarScrollBehavior.state.collapsedFraction) * 2.6)) * 40).sp

    LargeTopAppBar(
        scrollBehavior =  topBarScrollBehavior,
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

                    Screens.SettingsStopwatchBackgroundEffects.route -> {
                        "Background Effects"
                    }

                    Screens.SettingsTimerBackgroundEffects.route -> {
                        "Background Effects"
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

                    Screens.SettingsTimerScrollsHapticFeedback.route ->{
                        "Haptic Feedback"
                    }

                    else -> {
                        ""
                    }
                },
                fontSize = if (topBarScrollBehavior.state.collapsedFraction <= 0.545) titleFontSize * 0.90 else titleFontSize * 1.10,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = if (topBarScrollBehavior.state.collapsedFraction <= 0.545) TextAlign.Center else TextAlign.Start,
                style = TextStyle()
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Black00),
        navigationIcon =
        {
            IconButton(
                enabled = navController.previousBackStackEntry != null && currentRoute != Screens.Stopwatch.route && currentRoute != Screens.Timer.route,
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .alpha(
                        if (navController.previousBackStackEntry != null && currentRoute != Screens.Stopwatch.route
                            && currentRoute != Screens.Timer.route
                        ) 1f else 0f
                    )
                    .wrapContentHeight(align = Alignment.Bottom),

                ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        },
        actions = {
            IconButton(
                enabled = false,
                onClick = {},
                modifier = Modifier
                    .alpha(alpha = 0f)
                    .wrapContentHeight(align = Alignment.Bottom),
                ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )

}

