package com.kingfu.clok.view.bugReport

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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.SpanStyle
import androidx.compose .ui.text.buildAnnotatedString
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
    val bugReportEmailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", bugReportEmail, null))
    val discordInviteLink = "https://discord.gg/wssjhZavc2"
    val discordIntent = Intent(Intent.ACTION_VIEW)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black00)
            .verticalScroll(bugReportScrollState)
    ) {
        Card(
            modifier = Modifier.padding(5.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
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
                            Icons.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                }
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .background(Color.Transparent)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            context.startActivity(Intent.createChooser(bugReportEmailIntent, "Send email..."))
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
                            context.startActivity(Intent.createChooser(bugReportEmailIntent, "Send email..."))
                        }
                    ) {
                        Icon(
                            Icons.Filled.Email,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                }
            }
        }


    }
}