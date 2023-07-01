package com.kingfu.clok.settings.settingsView.settingsPrivacyView

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SettingsPrivacyPolicyView(
    haptic: HapticFeedback,
    context: Context
) {

    val privacyPolicyLink = "https://github.com/ClokApp/Clok-Support/blob/main/Privacy%20Policy.md"
    val privacyPolicyIntent = Intent(Intent.ACTION_VIEW)

    Text(
        text = "Privacy",
        modifier = Modifier.padding(start = 26.dp, end = 32.dp, top = 12.dp, bottom = 6.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onTertiaryContainer
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .clickable {
                haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                privacyPolicyIntent.data = Uri.parse(privacyPolicyLink)
                context.startActivity(privacyPolicyIntent)
            }
            .background(color = MaterialTheme.colorScheme.inverseOnSurface.copy(0.40f))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Privacy Policy",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}
