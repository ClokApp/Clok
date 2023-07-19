package com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.fontStyle

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyle

@Composable
fun SettingsTimerFontStylesScreen(
    vm: SettingsViewModelTimer = viewModel(),
    navigateToSettingsTimerSelectedFontStyle: () -> Unit
) {

    val haptic = LocalHapticFeedback.current
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
            for (i in vm.fontStyleOptions.indices) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                            settingsTimerSelectedFontStyle = vm.fontStyleOptions.elementAt(index = i)
                            navigateToSettingsTimerSelectedFontStyle()
                        }
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Start,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = vm.fontStyleOptions.elementAt(index = i),
                        fontSize = 18.sp,
                        fontWeight = Normal,
                        color = White,
                        style = TextStyle()
                    )
                }
                if (i != vm.fontStyleOptions.size - 1) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = DarkGray.copy(alpha = 0.75f),
                        thickness = 0.8.dp
                    )
                }
            }
        }
    }
}
