package com.kingfu.clok.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AllOut
import androidx.compose.material.icons.rounded.Balance
import androidx.compose.material.icons.rounded.Balcony
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.ui.components.MyCard
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.components.MyIcon
import com.kingfu.clok.ui.components.MyRadioButton
import com.kingfu.clok.ui.components.MySlider
import com.kingfu.clok.ui.components.MySwitch
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerBody
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMedium
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.theme.TextTitleMedium
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.theme.themeBackgroundColor

@Composable
fun OneUI(
    modifier: Modifier = Modifier,
    middleColumn: @Composable () -> Unit,
    header: (@Composable () -> Unit)? = null,
    leftColumn: (@Composable () -> Unit)? = null,
    rightColumn: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    isClickable: Boolean = true,
    roundedCornerShape: RoundedCornerShape = RoundedCornerBody
) {
    Column(modifier = modifier) {
        if (header != null) {
            header()
        }

        MyCard(
            roundedCornerShape = roundedCornerShape,
            onClick = onClick,
            isClickable = isClickable,
            content = {
                if (leftColumn != null) {
                    Column(
                        modifier = Modifier.padding(end = 6.dp),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(space = 6.dp)
                    ) {
                        leftColumn.invoke()
                    }
                }

                Column(modifier = Modifier.weight(weight = 1f)) {
                    middleColumn()
                }


                if (rightColumn != null) {
                    Column(
                        modifier = Modifier.padding(start = 6.dp),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(space = 6.dp)
                    ) {
                        rightColumn()
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OneUiPreview() {

    var isChecked by rememberSaveable { mutableStateOf(value = true) }

    fun onCheckedChange() {
        isChecked = !isChecked
    }

    var sliderValue by rememberSaveable { mutableFloatStateOf(value = 80f) }
    val isClickable by rememberSaveable { mutableStateOf(value = true) }

    val radioOption =
        listOf("Hello World", "Title", "Exploring the Intricacies of Human Cognitive")
    var selectedRadioOption by rememberSaveable { mutableStateOf(value = radioOption[0]) }

    val theme = ThemeType.Dark
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        Column(modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .background(color = themeBackgroundColor(theme = theme))
        ) {
            OneUI(
                header = { TextBodyMediumHeading(text = stringResource(id = R.string.settings_timer_font_styles_name_id)) },
                middleColumn = { TextBodyLarge(text = radioOption[0]) },
                roundedCornerShape = RoundedCornerHeader,
                onClick = { selectedRadioOption = radioOption[0] },
                leftColumn = { MyRadioButton(selected = radioOption[0] == selectedRadioOption) }
            )

            MyHorizontalDivider()

            OneUI(
                middleColumn = { TextBodyLarge(text = radioOption[1]) },
                onClick = { selectedRadioOption = radioOption[1] },
                leftColumn = {
                    MyRadioButton(
                        selected = radioOption[1] == selectedRadioOption,
                        isEnabled = isClickable
                    )
                }
            )

            MyHorizontalDivider()

            OneUI(
                middleColumn = {
                    TextBodyMedium(
                        text = "This is an error description.",
                        color = colorScheme.error
                    )
                    TextBodyLarge(text = radioOption[2])
                    TextBodyMedium(
                        text = "Exploring the Intricacies of Human Cognitive Development and its Impact on Education"
                    )
                    MySlider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        content = {
                            MyIcon(
                                imageVector = Icons.Rounded.AllOut,
                                tint = colorScheme.inversePrimary
                            )
                            TextTitleMedium(text = "${sliderValue.toInt()} Hello World")
                        }
                    )
                },
                roundedCornerShape = RoundedCornerFooter,
                onClick = {
                    isChecked = !isChecked
                    selectedRadioOption = radioOption[2]
                },
                isClickable = isClickable,
                rightColumn = {
                    MyIcon(
                        imageVector = Icons.Rounded.Balance,
                        modifier = Modifier.alpha(
                            alpha = if (isClickable) 1f else 0.50f
                        )
                    )

                    MySwitch(
                        isChecked = isChecked,
                        onCheckedChange = { onCheckedChange() },
                        isEnabled = isClickable
                    )

                    MyRadioButton(
                        selected = radioOption[2] == selectedRadioOption,
                        isEnabled = isClickable
                    )
                },
                leftColumn = {
                    MyIcon(
                        imageVector = Icons.Rounded.Balcony,
                        modifier = Modifier.alpha(
                            alpha = if (isClickable) 1f else 0.50f
                        )
                    )

                    MyRadioButton(
                        selected = radioOption[2] == selectedRadioOption,
                        isEnabled = isClickable
                    )

                    MySwitch(
                        isChecked = isChecked,
                        onCheckedChange = { onCheckedChange() },
                        isEnabled = isClickable
                    )
                }
            )
        }
    }
}
