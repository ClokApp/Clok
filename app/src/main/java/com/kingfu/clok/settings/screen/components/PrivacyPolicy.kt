package com.kingfu.clok.settings.screen.components

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography

@Composable
fun PrivacyPolicy() {
    val context = LocalContext.current
    val privacyPolicyLink = "https://github.com/ClokApp/Clok-Support/blob/main/Privacy%20Policy.md"
    val privacyPolicyIntent = Intent(ACTION_VIEW)

    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    privacyPolicyIntent.data = Uri.parse(privacyPolicyLink)
                    context.startActivity(privacyPolicyIntent)
                }
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Policy,
                contentDescription = null,
                tint = colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(width = 16.dp))

            Text(
                text = stringResource(id = R.string.privacy_policy),
                style = typography.bodyLarge
            )
        }
    }

}

@PreviewLightDark
@Composable
private fun PrivacyPolicyPreview() {
    ClokThemePreview { PrivacyPolicy() }
}






