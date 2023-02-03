package com.kingfu.clok.bugReport

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.customFontSize

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black00)
            .verticalScroll(bugReportScrollState)
    ) {
        Card(
            shape = RoundedCornerShape(30.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .background(Color.Black.copy(0.4f))
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            discordIntent.data = Uri.parse(discordInviteLink)
                            context.startActivity(discordIntent)
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),

                        ) {
                        Text(
                            text = "Discord",
                            fontSize = customFontSize(textUnit = 18.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color(88, 101, 242, 255)
                        )

                        Text(
                            text = "Report bug via Clok's Discord server.",
                            fontSize = customFontSize(textUnit = 14.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }

                    IconButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            discordIntent.data = Uri.parse(discordInviteLink)
                            context.startActivity(discordIntent)
                        }
                    ) {
                        Icon(
                            Icons.Rounded.ArrowForward,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                }
                Divider(
                    modifier = Modifier
                        .background(Color.Black.copy(0.4f))
                        .padding(horizontal = 24.dp),
                    color = Color.DarkGray,
                )

                Row(
                    modifier = Modifier
                        .background(Color.Black.copy(0.4f))
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            context.startActivity(Intent.createChooser(
                                bugReportEmailIntent,
                                "Send email...")
                            )
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),

                        ) {
                        Text(
                            text = "Email",
                            fontSize = customFontSize(textUnit = 18.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color(234, 67, 53, 255)
                        )

                        Text(
                            text = buildAnnotatedString {
                                append("Send an email to ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append(bugReportEmail)
                                }
                                append(".")
                            },
                            fontSize = customFontSize(textUnit = 14.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }

                    IconButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            context.startActivity(
                                Intent.createChooser(
                                    bugReportEmailIntent,
                                    "Send email..."
                                )
                            )
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Email,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black00)
        ) {

            Text(
                text = "Potential bug fix",
                modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 2.dp),
                fontSize = customFontSize(textUnit = 16.sp),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )

            Card(
                shape = RoundedCornerShape(30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.Black.copy(0.4f))
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                    ) {
                        Text(
                            text = "Clear App Data",
                            fontSize = customFontSize(textUnit = 18.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                        )
                        Text(
                            text = "1. Go to your phone's Settings app.\n" +
                                    "2. Tap on the \"Apps\" or \"Application Manager\" option.\n" +
                                    "3. Find the app you want to clear data for and tap on it.\n" +
                                    "4. Tap on the \"Clear Data\" button. (might have to go to storage tab first)\n" +
                                    "5. Confirm that you want to clear the app data by tapping \"OK\" on the pop-up window.\n\n" +
                            "Note: Clearing app data will erase any personalization and customization that you have done within the app.\n\n" +
                            "Last resort would be reinstall the application.",

                            fontSize = customFontSize(textUnit = 14.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                }

            }
        }

    }
}

