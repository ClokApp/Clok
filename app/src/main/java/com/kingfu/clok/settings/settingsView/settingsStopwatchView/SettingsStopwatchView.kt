package com.kingfu.clok.settings.settingsView.settingsStopwatchView

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShutterSpeed
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.util.NoRippleTheme
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsStopwatchView(
    navController: NavHostController,
    vm: SettingsViewModelStopwatch,
    haptic: HapticFeedback,
    configuration: Configuration
) {

    val interactionSource = remember { MutableInteractionSource() }
    val orientation = configuration.orientation
    val screenHeight = configuration.screenHeightDp.dp

    Text(
        text = Screens.Stopwatch.name,
        modifier = Modifier.padding(start = 26.dp, end = 32.dp, top = 12.dp, bottom = 6.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onTertiaryContainer
    )

    Card(
        shape = RoundedCornerShape(size = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface.copy(0.40f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    vm.stopwatchSetShowLabelCheckState()
                    vm.saveStopwatchShowLabel()
                }
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Show label",
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                color = Color.White

            )
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                Switch(
                    modifier = Modifier.scale(scale = 0.85f),
                    checked = vm.stopwatchShowLabel,
                    onCheckedChange = {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        vm.stopwatchSetShowLabelCheckState()
                        vm.saveStopwatchShowLabel()
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedTrackColor = Color.Gray,
                    ),
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = navController.currentDestination?.route == Screens.Settings.route) {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    navController.navigate(route = Screens.SettingsStopwatchFontStyles.route)
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Screens.SettingsStopwatchFontStyles.name,
                fontSize = 18.sp,
                color = Color.White

            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(navController.currentDestination?.route == Screens.Settings.route) {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    navController.navigate(route = Screens.SettingsStopwatchLabelStyles.route)
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Screens.SettingsStopwatchLabelStyles.name,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(navController.currentDestination?.route == Screens.Settings.route) {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    navController.navigate(route = Screens.SettingsStopwatchBackgroundEffects.route)
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = Screens.SettingsStopwatchBackgroundEffects.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )

                Text(
                    text = "Enable when show label is turned on and stopwatch is running.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Stopwatch refresh rate",
                fontSize = 18.sp,
                color = Color.White
            )

            Text(
                text = "Stopwatch is delayed before refreshing.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            screenHeight * 0.05f
                        } else {
                            screenHeight * 0.12f
                        }
                    )
            ) {
                Slider(
                    value = vm.stopwatchRefreshRateValue,
                    valueRange = 1f..100f,
                    onValueChange = {
                        vm.updateStopwatchRefreshRateValue(float = it)
                        vm.saveStopwatchRefreshRateValue()
                    },
                    colors = SliderDefaults.colors(
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                    ),
                    modifier = Modifier
                        .matchParentSize()
                        .padding(horizontal = 16.dp)
                        .clip(shape = RoundedCornerShape(size = 10.dp))
                        .scale(
                            scaleX = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1.10f else 1.03f,
                            scaleY = 100f
                        ),
                    thumb = {
                        SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            thumbSize = DpSize.Zero
                        )
                    },
                )

                Row(
                    modifier = Modifier.matchParentSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ShutterSpeed,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )

                    Spacer(modifier = Modifier.width(width = 10.dp))

                    Text(
                        text = "${vm.stopwatchRefreshRateValue.roundToInt()} ms delay",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}