package com.kingfu.clok.settings.settingsView.settingsTimerView.fontStyle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.ui.theme.Green50
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyleTopBarName

@Composable
fun SettingsTimerFontStyles(
    vm: SettingsViewModelTimer = viewModel(),
    navController: NavHostController
) {

    val haptic = LocalHapticFeedback.current
    val options = setOf("Scrolls font style", "Timer time font style")
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black00)
            .verticalScroll(state = scrollState)
    ) {

        Text(
            text = "${Screens.Timer.name} ${Screens.SettingsTimerFontStyles.name}",
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 4.dp),
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            color = Green50.copy(alpha = 0.5f),
        )

        Card(
            shape = RoundedCornerShape(size = 30.dp)
        ) {
            Column {
                for (i in options.indices) {
                    Row(
                        modifier = Modifier
                            .background(Color.Black.copy(0.4f))
                            .fillMaxWidth()
                            .clickable {
                                haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                                navController.navigate(route = Screens.SettingsTimerSelectedFontStyle.route)
                                settingsTimerSelectedFontStyleTopBarName = options.elementAt(index = i)
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = options.elementAt(index = i),
                            modifier = Modifier.padding(all = 8.dp),
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    if (i != options.size - 1) {
                        Divider(
                            modifier = Modifier
                                .background(color = Color.Black.copy(alpha = 0.4f))
                                .padding(horizontal = 24.dp),
                            color = Color.DarkGray,
                        )
                    }
                }
            }
        }

    }
}