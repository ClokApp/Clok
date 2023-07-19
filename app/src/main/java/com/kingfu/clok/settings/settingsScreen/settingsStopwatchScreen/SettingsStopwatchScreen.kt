package com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
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
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderDefaults.colors
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.DpSize.Companion.Zero
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.util.NoRippleTheme
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsStopwatchScreen(
    vm: SettingsViewModelStopwatch,
    haptic: HapticFeedback,
    configuration: Configuration,
    navigateToSettingsStopwatchBackgroundEffects: () -> Unit,
    navigateToSettingsStopwatchFontStyles: () -> Unit,
    navigateToSettingsStopwatchLabelStyles: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val orientation by remember{ mutableIntStateOf(value =  configuration.orientation) }
    val screenHeight by remember{ mutableStateOf(value = configuration.screenHeightDp.dp)}

    Text(
        text = Screens.Stopwatch.name,
        modifier = Modifier.padding(start = 26.dp, end = 32.dp, top = 12.dp, bottom = 6.dp),
        fontSize = 16.sp,
        fontWeight = SemiBold,
        color = colorScheme.onTertiaryContainer
    )

    Card(
        shape = RoundedCornerShape(size = 20.dp),
        colors = cardColors(
            containerColor = colorScheme.inverseOnSurface.copy(alpha = 0.40f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    vm.stopwatchSetShowLabelCheckState()
                    vm.saveStopwatchShowLabel()
                }
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = "Show label",
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                color = White,
                style = TextStyle()
            )
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                Switch(
                    modifier = Modifier.scale(scale = 0.85f),
                    checked = vm.stopwatchShowLabel,
                    onCheckedChange = {
                        haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                        vm.stopwatchSetShowLabelCheckState()
                        vm.saveStopwatchShowLabel()
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = White,
                        uncheckedThumbColor = White,
                        checkedTrackColor = colorScheme.primary,
                        uncheckedTrackColor = Gray,
                    ),
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    navigateToSettingsStopwatchFontStyles()
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = Screens.SettingsStopwatchFontStyles.name,
                fontSize = 18.sp,
                color = White,
                style = TextStyle()
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    navigateToSettingsStopwatchLabelStyles()
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),

            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = Screens.SettingsStopwatchLabelStyles.name,
                fontSize = 18.sp,
                color = White,
                style = TextStyle()
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    navigateToSettingsStopwatchBackgroundEffects()
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Column {
                Text(
                    text = Screens.SettingsStopwatchBackgroundEffects.name,
                    fontSize = 18.sp,
                    color = White,
                    style = TextStyle()
                )

                Text(
                    text = "Enable when show label is turned on and stopwatch is running.",
                    fontSize = 14.sp,
                    color = Gray,
                    lineHeight = 18.sp,
                    style = TextStyle()
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Start,
            verticalArrangement = Center
        ) {
            Text(
                text = "Stopwatch refresh rate",
                fontSize = 18.sp,
                color = White,
                style = TextStyle()
            )

            Text(
                text = "Stopwatch is delayed before refreshing.",
                fontSize = 14.sp,
                color = Gray,
                lineHeight = 18.sp,
                style = TextStyle()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(
                        if (orientation == ORIENTATION_PORTRAIT) {
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
                    colors = colors(
                        activeTrackColor = colorScheme.primary,
                        inactiveTrackColor = colorScheme.primary.copy(alpha = 0.5f),
                    ),
                    modifier = Modifier
                        .matchParentSize()
                        .padding(horizontal = 16.dp)
                        .clip(shape = RoundedCornerShape(size = 10.dp))
                        .scale(
                            scaleX = if (orientation == ORIENTATION_PORTRAIT) 1.10f else 1.03f,
                            scaleY = 100f
                        ),
                    thumb = {
                        SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            thumbSize = Zero
                        )
                    },
                )

                Row(
                    modifier = Modifier.matchParentSize(),
                    horizontalArrangement = Center,
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ShutterSpeed,
                        contentDescription = null,
                        tint = colorScheme.inversePrimary
                    )

                    Spacer(modifier = Modifier.width(width = 10.dp))

                    Text(
                        text = "${vm.stopwatchRefreshRateValue.roundToInt()} ms delay",
                        fontWeight = Bold,
                        color = colorScheme.inversePrimary,
                        overflow = Ellipsis,
                        fontSize = 18.sp,
                        style = TextStyle()
                    )
                }
            }
        }
    }
}