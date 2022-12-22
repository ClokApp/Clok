package com.kingfu.clok.topBar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.variable.Variable

@Composable
fun ShowMenu(navController: NavController) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(surface = Color.Black.copy(0.85f)),
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            expanded = Variable.showMenu,
            onDismissRequest = { Variable.showMenu = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    navController.navigate(Screens.Settings.route)
                    {
                        launchSingleTop = true
                        restoreState = true
                    }
                    Variable.showMenu = false
                },
            ) {
                Text(
                    text = "Settings",
                    fontSize = customFontSize(textUnit = 16.sp),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal
                )
            }

            DropdownMenuItem(
                onClick = {
                    navController.navigate(Screens.BugReport.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    Variable.showMenu = false
                },
            ) {
                Text(
                    text = "Bug report",
                    fontSize = customFontSize(textUnit = 16.sp),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}