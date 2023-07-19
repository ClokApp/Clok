package com.kingfu.clok.settings.settingsScreen.settingsTimerScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer


@Composable
fun SettingsTimerScrollsHapticFeedback(
    vm: SettingsViewModelTimer = viewModel()

) {
    val haptic = LocalHapticFeedback.current
    val radioOptions = setOf("Strong", "Weak", "Off")
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .verticalScroll(state = scrollState)
    ) {

        Text(
            text = Screens.Timer.name,
            modifier = Modifier.padding(start = 26.dp, end = 32.dp, top = 12.dp, bottom = 6.dp),
            fontSize = 16.sp,
            fontWeight = SemiBold,
            color = colorScheme.onTertiaryContainer,
            style = TextStyle()
        )

        Card(
            shape = RoundedCornerShape(size = 20.dp),
            colors = cardColors(
                containerColor = colorScheme.inverseOnSurface.copy(alpha = 0.40f)
            )
        ) {
            for (i in radioOptions.indices) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                            vm.updateScrollHapticFeedback(string = radioOptions.elementAt(index = i))
                            vm.saveScrollsHapticFeedback()
                        }
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalArrangement = Start,
                    verticalAlignment = CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        selected = (radioOptions.elementAt(index = i) == vm.timerScrollsHapticFeedback),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(selectedColor = colorScheme.primary)
                    )
                    Text(
                        text = radioOptions.elementAt(index = i),
                        fontSize = 18.sp,
                        color = Color.White,
                        style = TextStyle()
                    )
                }
                if (i != radioOptions.size - 1) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = Color.DarkGray.copy(alpha = 0.75f),
                        thickness = 0.8.dp
                    )
                }
            }
        }
    }
}