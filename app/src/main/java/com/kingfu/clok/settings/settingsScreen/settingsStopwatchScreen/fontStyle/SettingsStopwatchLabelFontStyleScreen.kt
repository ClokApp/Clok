package com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.fontStyle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.stopwatch.feature.fontStyle.StopwatchFontStyleType
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.components.MyRadioButton
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.theme.TextDisplayLarge
import com.kingfu.clok.ui.util.screenHeight


@Composable
fun SettingsStopwatchLabelFontStyleScreen(
    updateStopwatchLabelFontStyle: (String) -> Unit,
    saveStopwatchLabelFontStyle: () -> Unit,
    stopwatchLabelFontStyle: String
) {

    val default = StopwatchFontStyleType.Default.name
    val innerStroke = StopwatchFontStyleType.InnerStroke.name

    OneUI(
        header = {
            TextBodyMediumHeading(
                text = "${stringResource(id = R.string.stopwatch_name_id)} " +
                        stringResource(id = R.string.settings_stopwatch_font_styles_name_id)
            )
        },
        middleColumn = { TextDisplayLarge(text = stringResource(id = R.string.settings_screen_default_font_style)) },
        roundedCornerShape = RoundedCornerHeader,
        onClick = {
            updateStopwatchLabelFontStyle(default)
            saveStopwatchLabelFontStyle()
        },
        leftColumn = { MyRadioButton(selected = default == stopwatchLabelFontStyle) }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextDisplayLarge(
                text = stringResource(id = R.string.settings_screen_inner_stroke_font_style),
                style = TextStyle(
                    drawStyle = Stroke(
                        width = 5f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            )
        },
        roundedCornerShape = RoundedCornerFooter,
        onClick = {
            updateStopwatchLabelFontStyle(innerStroke)
            saveStopwatchLabelFontStyle()
        },
        leftColumn = { MyRadioButton(selected = innerStroke == stopwatchLabelFontStyle) }
    )

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.90f))
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsStopwatchLabelFontStyleScreen() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsStopwatchLabelFontStyleScreen(
                updateStopwatchLabelFontStyle = {},
                saveStopwatchLabelFontStyle = {},
                stopwatchLabelFontStyle = StopwatchFontStyleType.Default.name
            )
        }
    }
}
