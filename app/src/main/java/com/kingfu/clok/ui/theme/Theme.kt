package com.kingfu.clok.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.kingfu.clok.core.ThemeType
import darkBackground
import darkError
import darkErrorContainer
import darkInverseOnSurface
import darkInversePrimary
import darkInverseSurface
import darkOnBackground
import darkOnError
import darkOnErrorContainer
import darkOnPrimary
import darkOnPrimaryContainer
import darkOnSecondary
import darkOnSecondaryContainer
import darkOnSurface
import darkOnSurfaceVariant
import darkOnTertiary
import darkOnTertiaryContainer
import darkOutline
import darkOutlineVariant
import darkPrimary
import darkPrimaryContainer
import darkScrim
import darkSecondary
import darkSecondaryContainer
import darkSurface
import darkSurfaceBright
import darkSurfaceContainer
import darkSurfaceContainerHigh
import darkSurfaceContainerHighest
import darkSurfaceContainerLow
import darkSurfaceContainerLowest
import darkSurfaceDim
import darkSurfaceTint
import darkSurfaceVariant
import darkTertiary
import darkTertiaryContainer
import lightBackground
import lightError
import lightErrorContainer
import lightInverseOnSurface
import lightInversePrimary
import lightInverseSurface
import lightOnBackground
import lightOnError
import lightOnErrorContainer
import lightOnPrimary
import lightOnPrimaryContainer
import lightOnSecondary
import lightOnSecondaryContainer
import lightOnSurface
import lightOnSurfaceVariant
import lightOnTertiary
import lightOnTertiaryContainer
import lightOutline
import lightOutlineVariant
import lightPrimary
import lightPrimaryContainer
import lightScrim
import lightSecondary
import lightSecondaryContainer
import lightSurface
import lightSurfaceBright
import lightSurfaceContainer
import lightSurfaceContainerHigh
import lightSurfaceContainerHighest
import lightSurfaceContainerLow
import lightSurfaceContainerLowest
import lightSurfaceDim
import lightSurfaceTint
import lightSurfaceVariant
import lightTertiary
import lightTertiaryContainer


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
    theme: ThemeType = ThemeType.DARK,
    content: @Composable () -> Unit,
) {
    val colorScheme = when (theme) {
        ThemeType.DARK -> darkColorScheme
        ThemeType.LIGHT -> lightColorScheme
    }


    val view = LocalView.current
    val isLightStatusBar = theme == ThemeType.LIGHT
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                isLightStatusBar
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                isLightStatusBar
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

@Composable
fun ClokThemePreview(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if(isDarkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
