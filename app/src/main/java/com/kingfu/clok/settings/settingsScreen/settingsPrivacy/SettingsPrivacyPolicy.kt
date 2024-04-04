package com.kingfu.clok.settings.settingsScreen.settingsPrivacy

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerHeaderAndFooter
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.theme.ThemeType


@Composable
fun SettingsPrivacyPolicy(context: Context) {

    val privacyPolicyLink = stringResource(id = R.string.privacy_policy_link)
    val privacyPolicyIntent = Intent(ACTION_VIEW)

    OneUI(
        roundedCornerShape = RoundedCornerHeaderAndFooter,
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.privacy)) },
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.privacy_policy)) },
        onClick = {
            privacyPolicyIntent.data = Uri.parse(privacyPolicyLink)
            context.startActivity(privacyPolicyIntent)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsPrivacyPolicyComponent() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
          SettingsPrivacyPolicy(context = LocalContext.current)
        }
    }
}
