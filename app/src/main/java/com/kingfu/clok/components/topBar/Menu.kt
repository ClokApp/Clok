package com.kingfu.clok.components.topBar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.variable.Variable.showMenu

@Composable
fun Menu(navController: NavController) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            surface = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.40f)
        ),
        shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(size = 10.dp))
    ) {
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = Screens.Settings.name,
                        fontSize = 16.sp,
                        color = Color.White,
                        style = TextStyle()
                    )
                },
                onClick = {
                    navController.navigate(route = Screens.Settings.route)
                    {
                        launchSingleTop = true
                        restoreState = true
                    }
                    showMenu = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = Screens.BugReport.name,
                        fontSize = 16.sp,
                        color = Color.White,
                        style = TextStyle()
                    )
                },
                onClick = {
                    navController.navigate(route = Screens.BugReport.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    showMenu = false
                }
            )
        }
    }
}