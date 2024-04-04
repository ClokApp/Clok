package com.kingfu.clok.bugReport

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.components.MyIcon
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.RoundedCornerHeaderAndFooter
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMedium
import com.kingfu.clok.ui.theme.TextBodyMediumAnnotatedString
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.theme.themeBackgroundColor


@Composable
fun BugReportScreen() {
    // Testing
    val context = LocalContext.current
    val bugReportEmail = stringResource(id = R.string.bug_report_email)
    val bugReportEmailIntent =
        Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", bugReportEmail, null))
    val discordInviteLink = stringResource(id = R.string.discord_invite_link)
    val discordIntent = Intent(ACTION_VIEW)
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    OneUI(
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.discord))
            TextBodyMedium(text = stringResource(id = R.string.discord_report_bug))
        },
        rightColumn = { MyIcon(imageVector = Icons.AutoMirrored.Rounded.ArrowForward) },
        onClick = {
            discordIntent.data = Uri.parse(discordInviteLink)
            context.startActivity(discordIntent)
        },
        roundedCornerShape = RoundedCornerHeader
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.email))
            TextBodyMediumAnnotatedString(
                annotatedString = buildAnnotatedString {
                    append(text = stringResource(id = R.string.send_an_email_to) + " ")
                    withStyle(
                        style = SpanStyle(
                            color = colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(text = stringResource(id = R.string.bug_report_email))
                    }
                    append(text = ".")
                }
            )
        },
        onClick = {
            context.startActivity(
                Intent.createChooser(
                    bugReportEmailIntent,
                    "Send email..."
                )
            )
        },
        roundedCornerShape = RoundedCornerFooter
    )

    OneUI(
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.clear_app_data))
            TextBodyMedium(text = stringResource(id = R.string.clear_app_data_instructions))
        },
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.potential_bug_fix)) },
        roundedCornerShape = RoundedCornerHeaderAndFooter
    )

    Spacer(modifier = Modifier.height(height = screenHeight * 0.8f))

}

@Preview
@Composable
fun PreviewBugReportScreen() {
    val theme = ThemeType.Dark
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .background(color = themeBackgroundColor(theme = theme))
        ) {
            BugReportScreen()
        }
    }
}





