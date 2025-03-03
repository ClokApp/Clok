package com.kingfu.clok.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    screens: List<AppDestination> = listOf(AppDestination.STOPWATCH, AppDestination.TIMER),
    onClick: (AppDestination) -> Unit = {},
    navController: NavHostController,
    isSelected: (AppDestination) -> Boolean = { screen ->
        navController.currentDestination?.hierarchy?.any {
            it.hasRoute(route = screen.screen::class)
        } == true
    }
) {
    Surface {
        Row(modifier = modifier.windowInsetsPadding(insets = NavigationBarDefaults.windowInsets)) {
            screens.forEach { screen ->
                Box(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .height(height = 60.dp)
                        .clickable(
                            enabled = !isSelected(screen),
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onClick(screen) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = screen.label),
                        textAlign = TextAlign.Center,
                        color = if (isSelected(screen)) colorScheme.primary else colorScheme.outline,
                        textDecoration = if (isSelected(screen)) TextDecoration.Underline else TextDecoration.None,
                        fontSize = typography.bodyMedium.fontSize.value.nonScaledSp
                    )
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun BottomNavigationBarPreview() {

    var screen by remember { mutableStateOf(value = AppDestination.STOPWATCH) }

    ClokThemePreview {
        BottomNavigationBar(
            onClick = { screen = it },
            navController = rememberNavController(),
            isSelected = { screen == it }
        )
    }
}


