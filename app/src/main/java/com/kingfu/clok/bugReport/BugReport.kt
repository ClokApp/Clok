package com.kingfu.clok.bugReport

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BugReport() {

    val haptic = LocalHapticFeedback.current
    val bugReportScrollState = rememberScrollState()
    val context = LocalContext.current
    val bugReportEmail = "ClokBugReport@gmail.com"
    val bugReportEmailIntent =
        Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", bugReportEmail, null))
    val discordInviteLink = "https://discord.gg/wssjhZavc2"
    val discordIntent = Intent(Intent.ACTION_VIEW)
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp


    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .verticalScroll(state = bugReportScrollState)
    ) {
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
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        discordIntent.data = Uri.parse(discordInviteLink)
                        context.startActivity(discordIntent)
                    }
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.fillMaxWidth(fraction = 0.85f)) {
                    Text(
                        text = "Discord",
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    Text(
                        text = "Report bug via Clok's Discord server.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 18.sp
                    )
                }

                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
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
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        context.startActivity(
                            Intent.createChooser(
                                bugReportEmailIntent,
                                "Send email..."
                            )
                        )
                    }
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.fillMaxWidth(fraction = 0.85f)) {
                    Text(
                        text = "Email",
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(text = "Send an email to ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(text = bugReportEmail)
                            }
                            append(text = ".")
                        },
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 18.sp
                    )
                }

                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            text = "Potential bug fix",
            modifier = Modifier.padding(start = 26.dp, end = 32.dp, top = 12.dp, bottom = 6.dp),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 20.dp))
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                }
                .background(color = MaterialTheme.colorScheme.inverseOnSurface.copy(0.40f))
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Text(
                text = "Clear App Data",
                fontSize = 18.sp,
                color = Color.White
            )

            Text(
                text = "1. Go to your phone's Settings app.\n" +
                        "2. Tap on the \"Apps\" or \"Application Manager\" option.\n" +
                        "3. Find the app you want to clear data for and tap on it.\n" +
                        "4. Tap on the \"Clear Data\" button. (might have to go to storage tab first)\n" +
                        "5. Confirm that you want to clear the app data by tapping \"OK\" on the pop-up window.\n\n" +
                        "Note: Clearing app data will erase any personalization and customization that you have done within the app.\n\n" +
                        "Last resort would be reinstall the application.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(height = screenHeight * 0.2f))
    }
}

