package com.kingfu.clok.settings.settingsView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kingfu.clok.BuildConfig
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.SettingsStopwatchView
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerView
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.customFontSize

@Composable
fun SettingsView(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
) {
    val haptic = LocalHapticFeedback.current
    val settingsScrollState = rememberScrollState()

    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black00)
                .verticalScroll(settingsScrollState)
        ) {
            SettingsStopwatchView(
                navController,
                settingsViewModelStopwatch,
                haptic,
            )

            SettingsTimerView(
                navController = navController,
                settingsViewModelTimer,
                haptic
            )


            Text(
                text = "About App",
                modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 2.dp),
                fontSize = customFontSize(textUnit = 16.sp),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )

            Card(
                shape = RoundedCornerShape(30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.Black.copy(0.4f))
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                    ) {
                        Text(
                            text = "Current Version ${BuildConfig.VERSION_NAME}",
                            fontSize = customFontSize(textUnit = 18.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                        )

                        Text(
                            text = "Made using Kotlin and Jetpack Compose",
                            fontSize = customFontSize(textUnit = 14.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))

        }
    }
}