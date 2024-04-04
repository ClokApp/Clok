package com.kingfu.clok.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

// Dark Theme
val darkOnTertiaryContainer = Color(color = 0xFFBCECE5)
val darkOutlineVariant = Color(color = 0xFF474742)
val darkSurfaceVariant = Color(color = 0xFF45483D)
val darkSurfaceContainerHigh = Color(color = 0xFF292A25)
val darkOnError = Color(color = 0xFF601410)
val darkOnBackground = Color(color = 0xFFE3E3DB)
val darkOnSurface = Color(color = 0xFFE3E3DB)
val darkSurfaceContainer = Color(color = 0xFF1F201B)
val darkInverseSurface = Color(color = 0xFFE3E3DB)
val darkSurface = Color(color = 0xFF1B1C17)
val darkSurfaceContainerHighest = Color(color = 0xFF343530)
val darkSurfaceBright = Color(color = 0xFF393A34)
val darkOnPrimary = Color(color = 0xFF223600)
val darkPrimary = Color(color = 0xFFB5D086)
val darkOnTertiary = Color(color = 0xFF013733)
val darkOnSecondaryContainer = Color(color = 0xFFDDE6C6)
val darkInverseOnSurface = Color(color = 0xFF2F312C)
val darkOnErrorContainer = Color(color = 0xFFF9DEDC)
val darkOnPrimaryContainer = Color(color = 0xFFD1ECA0)
val darkPrimaryContainer = Color(color = 0xFF384D13)
val darkSecondary = Color(color = 0xFFC1CAAB)
val darkTertiaryContainer = Color(color = 0xFF204E4A)
val darkSurfaceTint = Color(color = 0xFFB5D086)
val darkSecondaryContainer = Color(color = 0xFF414A32)
val darkBackground = Color(color = 0xFF1B1C17)
val darkTertiary = Color(color = 0xFFA0D0C9)
val darkScrim = Color(color = 0xFF000000)
val darkError = Color(color = 0xFFF2B8B5)
val darkSurfaceContainerLowest = Color(color = 0xFF0E0F06)
val darkOutline = Color(color = 0xFF909285)
val darkOnSecondary = Color(color = 0xFF2B331E)
val darkOnSurfaceVariant = Color(color = 0xFFC5C8B9)
val darkInversePrimary = Color(color = 0xFF4F6628)
val darkErrorContainer = Color(color = 0xFF8C1D18)
val darkSurfaceContainerLow = Color(color = 0xFF1B1C17)
val darkSurfaceDim = Color(color = 0xFF13140D)


// Light Theme
val lightOnTertiaryContainer = Color(0xFF00201d)
val lightOutlineVariant = Color(0xFFc5c8b9)
val lightSurfaceVariant = Color(0xFFe2e4d4)
val lightSurfaceContainerHigh = Color(0xFFe5eadb)
val lightOnError = Color(0xFFFFFFFF)
val lightOnBackground = Color(0xFF191d13)
val lightOnSurface = Color(0xFF191d13)
val lightSurfaceContainer = Color(0xFFebf0e0)
val lightInverseSurface = Color(0xFF2e3227)
val lightSurface = Color(0xFFf6fbec)
val lightSurfaceContainerHighest = Color(0xFFe2e4d4)
val lightSurfaceBright = Color(0xFFf6fbec)
val lightOnPrimary = Color(0xFFFFFFFF)
val lightPrimary = Color(0xFF4f6628)
val lightOnTertiary = Color(0xFFFFFFFF)
val lightOnSecondaryContainer = Color(0xFF171e0a)
val lightInverseOnSurface = Color(0xFFf0f2e3)
val lightOnErrorContainer = Color(0xFF410e0b)
val lightOnPrimaryContainer = Color(0xFF121f00)
val lightPrimaryContainer = Color(0xFFd1eca0)
val lightSecondary = Color(0xFF596248)
val lightTertiaryContainer = Color(0xFFbcEce5)
val lightSurfaceTint = Color(0xFF4f6628)
val lightSecondaryContainer = Color(0xFFdde6c6)
val lightBackground = Color(0xFFf6fbec)
val lightTertiary = Color(0xFF396662)
val lightScrim = Color(0xFF000000)
val lightError = Color(0xFFb3261e)
val lightSurfaceContainerLowest = Color(0xFFFFFFFF)
val lightOutline = Color(0xFF74786b)
val lightOnSecondary = Color(0xFFFFFFFF)
val lightOnSurfaceVariant = Color(0xFF45483d)
val lightInversePrimary = Color(0xFFb5d086)
val lightErrorContainer = Color(0xFFf9dedc)
val lightSurfaceContainerLow = Color(0xFFf1f5e6)
val lightSurfaceDim = Color(0xFFd7dccd)


@Composable
fun themeBackgroundColor(
    theme: ThemeType
): Color {
    return when(theme){
        ThemeType.Dark -> Black
        ThemeType.Light -> White
        ThemeType.System -> if(isSystemInDarkTheme()) Black else White
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun getAllThemeColorARGB(): String {

    var allColorsARGB = ""

    val colors = hashMapOf(
        "Primary" to colorScheme.primary,
        "OnPrimary" to colorScheme.onPrimary,
        "PrimaryContainer" to colorScheme.primaryContainer,
        "OnPrimaryContainer" to colorScheme.onPrimaryContainer,
        "InversePrimary" to colorScheme.inversePrimary,
        "Secondary" to colorScheme.secondary,
        "OnSecondary" to colorScheme.onSecondary,
        "SecondaryContainer" to colorScheme.secondaryContainer,
        "OnSecondaryContainer" to colorScheme.onSecondaryContainer,
        "Tertiary" to colorScheme.tertiary,
        "OnTertiary" to colorScheme.onTertiary,
        "TertiaryContainer" to colorScheme.tertiaryContainer,
        "OnTertiaryContainer" to colorScheme.onTertiaryContainer,
        "Background" to colorScheme.background,
        "OnBackground" to colorScheme.onBackground,
        "Surface" to colorScheme.surface,
        "OnSurface" to colorScheme.onSurface,
        "SurfaceVariant" to colorScheme.surfaceVariant,
        "OnSurfaceVariant" to colorScheme.onSurfaceVariant,
        "SurfaceTint" to colorScheme.surfaceTint,
        "InverseSurface" to colorScheme.inverseSurface,
        "InverseOnSurface" to colorScheme.inverseOnSurface,
        "Error" to colorScheme.error,
        "OnError" to colorScheme.onError,
        "ErrorContainer" to colorScheme.errorContainer,
        "OnErrorContainer" to colorScheme.onErrorContainer,
        "Outline" to colorScheme.outline,
        "OutlineVariant" to colorScheme.outlineVariant,
        "Scrim" to colorScheme.scrim,
        "SurfaceBright" to colorScheme.surfaceBright,
        "SurfaceDim" to colorScheme.surfaceDim,
        "SurfaceContainer" to colorScheme.surfaceContainer,
        "SurfaceContainerHigh" to colorScheme.surfaceContainerHigh,
        "SurfaceContainerHighest" to colorScheme.surfaceContainerHighest,
        "SurfaceContainerLow" to colorScheme.surfaceContainerLow,
        "SurfaceContainerLowest" to colorScheme.surfaceContainerLowest
    )

    for ((name, color) in colors) {
        allColorsARGB += "$name: ${color.value.toHexString().substring(0, 8)}\n"
    }

    return allColorsARGB

}


