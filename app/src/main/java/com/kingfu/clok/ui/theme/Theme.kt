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
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.kingfu.clok.core.ThemeType


private val darkColorScheme = darkColorScheme(
    primary = darkPrimary,
    onPrimary = darkOnPrimary,
    primaryContainer = darkPrimaryContainer,
    onPrimaryContainer = darkOnPrimaryContainer,
    inversePrimary = darkInversePrimary,
    secondary = darkSecondary,
    onSecondary = darkOnSecondary,
    secondaryContainer = darkSecondaryContainer,
    onSecondaryContainer = darkOnSecondaryContainer,
    tertiary = darkTertiary,
    onTertiary = darkOnTertiary,
    tertiaryContainer = darkTertiaryContainer,
    onTertiaryContainer = darkOnTertiaryContainer,
    background = darkBackground,
    onBackground = darkOnBackground,
    surface = darkSurface,
    onSurface = darkOnSurface,
    surfaceVariant = darkSurfaceVariant,
    onSurfaceVariant = darkOnSurfaceVariant,
    surfaceTint = darkSurfaceTint,
    inverseSurface = darkInverseSurface,
    onError = darkOnError,
    onErrorContainer = darkOnErrorContainer,
    outline = darkOutline,
    outlineVariant = darkOutlineVariant,
    scrim = darkScrim,
    surfaceBright = darkSurfaceBright,
    surfaceDim = darkSurfaceDim,
    surfaceContainer = darkSurfaceContainer,
    surfaceContainerHigh = darkSurfaceContainerHigh,
    surfaceContainerHighest = darkSurfaceContainerHighest,
    surfaceContainerLow = darkSurfaceContainerLow,
    surfaceContainerLowest = darkSurfaceContainerLowest,
    error = darkError,
    errorContainer = darkErrorContainer,
    inverseOnSurface = darkInverseOnSurface
)


private val lightColorScheme = lightColorScheme(
    primary = lightPrimary,
    onPrimary = lightOnPrimary,
    primaryContainer = lightPrimaryContainer,
    onPrimaryContainer = lightOnPrimaryContainer,
    inversePrimary = lightInversePrimary,
    secondary = lightSecondary,
    onSecondary = lightOnSecondary,
    secondaryContainer = lightSecondaryContainer,
    onSecondaryContainer = lightOnSecondaryContainer,
    tertiary = lightTertiary,
    onTertiary = lightOnTertiary,
    tertiaryContainer = lightTertiaryContainer,
    onTertiaryContainer = lightOnTertiaryContainer,
    background = lightBackground,
    onBackground = lightOnBackground,
    surface = lightSurface,
    onSurface = lightOnSurface,
    surfaceVariant = lightSurfaceVariant,
    onSurfaceVariant = lightOnSurfaceVariant,
    surfaceTint = lightSurfaceTint,
    inverseSurface = lightInverseSurface,
    onError = lightOnError,
    onErrorContainer = lightOnErrorContainer,
    outline = lightOutline,
    outlineVariant = lightOutlineVariant,
    scrim = lightScrim,
    surfaceBright = lightSurfaceBright,
    surfaceDim = lightSurfaceDim,
    surfaceContainer = lightSurfaceContainer,
    surfaceContainerHigh = lightSurfaceContainerHigh,
    surfaceContainerHighest = lightSurfaceContainerHighest,
    surfaceContainerLow = lightSurfaceContainerLow,
    surfaceContainerLowest = lightSurfaceContainerLowest,
    error = lightError,
    errorContainer = lightErrorContainer,
    inverseOnSurface = lightInverseOnSurface
)

@Composable
fun ClokTheme(
    // Dynamic color is available on Android 12+
    theme: ThemeType,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
    isFullScreen: Boolean = false,
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            when (theme) {
                ThemeType.DARK -> dynamicDarkColorScheme(context)
                ThemeType.LIGHT -> dynamicLightColorScheme(context)
                ThemeType.SYSTEM -> if (isSystemInDarkTheme()){
                    dynamicDarkColorScheme(context)
                } else{
                    dynamicLightColorScheme(context)
                }
            }
        }

        (theme == ThemeType.DARK) -> darkColorScheme
        (theme == ThemeType.LIGHT) -> lightColorScheme
        else -> if (isSystemInDarkTheme()) darkColorScheme else lightColorScheme
    }


    val view = LocalView.current
    val isLightStatusBar = when (theme) {
        ThemeType.DARK -> if (isSystemInDarkTheme()) !isSystemInDarkTheme() else isSystemInDarkTheme()
        ThemeType.LIGHT -> if (isSystemInDarkTheme()) isSystemInDarkTheme() else !isSystemInDarkTheme()
        ThemeType.SYSTEM -> !isSystemInDarkTheme()
    }
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Transparent.toArgb()
            window.navigationBarColor = Transparent.toArgb()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                isLightStatusBar
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                isLightStatusBar

            val insetsController = WindowCompat.getInsetsController(window, window.decorView)

            if(isFullScreen) {
                insetsController.apply {
                    hide(WindowInsetsCompat.Type.statusBars())
                    hide(WindowInsetsCompat.Type.navigationBars())
                    systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }else{
                insetsController.apply {
                    show(WindowInsetsCompat.Type.statusBars())
                    show(WindowInsetsCompat.Type.navigationBars())
                    systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                }
            }
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )


}
