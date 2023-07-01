package com.kingfu.clok.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kingfu.clok.variable.Variable.DARK
import com.kingfu.clok.variable.Variable.LIGHT
import com.kingfu.clok.variable.Variable.appTheme


private val DarkColorScheme = darkColorScheme(
    primary = primary,
    secondary = secondary,
    secondaryContainer = secondaryContainer,
    tertiary = tertiary,
    tertiaryContainer = tertiaryContainer,
    onTertiaryContainer = onTertiaryContainer,
    inversePrimary = inversePrimary,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ClokTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = DynamicColors.isDynamicColorAvailable(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {


    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            when (appTheme) {
                DARK -> dynamicDarkColorScheme(context)
                LIGHT -> dynamicLightColorScheme(context)
                else -> if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                    context
                )
            }
        }
        (appTheme == DARK) -> DarkColorScheme
        (appTheme == LIGHT) -> LightColorScheme
        else -> if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        /* getting the current window by tapping into the Activity */
        val currentWindow = (view.context as? Activity)?.window
            ?: throw Exception("Not in an activity - unable to get Window reference")

        SideEffect {
            /* the default code did the same cast here - might as well use our new variable! */
//            currentWindow.statusBarColor = colorScheme.primary.toArgb()
            /* accessing the insets controller to change appearance of the status bar, with 100% less deprecation warnings */
//            WindowCompat.getInsetsController(currentWindow, view).isAppearanceLightStatusBars = !darkTheme
            currentWindow.statusBarColor = Black00.toArgb()
            currentWindow.navigationBarColor = Black00.toArgb()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                currentWindow.isNavigationBarContrastEnforced = false
            }
            WindowCompat.setDecorFitsSystemWindows(currentWindow, false)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = Color.Transparent, darkIcons =
        when (appTheme) {
            DARK ->  if(isSystemInDarkTheme()) !darkTheme else darkTheme
            LIGHT -> if(isSystemInDarkTheme()) darkTheme else !darkTheme
            else -> !isSystemInDarkTheme()
        }

    )
//    systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = !isDarkTheme)
}
