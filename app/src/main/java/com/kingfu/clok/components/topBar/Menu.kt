package com.kingfu.clok.components.topBar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.variable.Variable.showMenu

@Composable
fun Menu(
    navigateToSettingsScreen: () -> Unit,
    navigateToBugReportScreen: () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme.copy(
            surface = colorScheme.inverseOnSurface.copy(alpha = 0.40f)
        ),
        shapes = shapes.copy(extraSmall = RoundedCornerShape(size = 10.dp))
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
                        color = White,
                        style = TextStyle()
                    )
                },
                onClick = {
                    navigateToSettingsScreen()
                    showMenu = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = Screens.BugReport.name,
                        fontSize = 16.sp,
                        color = White,
                        style = TextStyle()
                    )
                },
                onClick = {
                    navigateToBugReportScreen()
                    showMenu = false
                }
            )
        }
    }
}