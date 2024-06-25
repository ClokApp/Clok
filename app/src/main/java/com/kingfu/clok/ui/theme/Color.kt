package com.kingfu.clok.ui.theme

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Dark Theme
val darkOnTertiaryContainer = Color(0xFFF7D9FF)
val darkOutlineVariant = Color(0xFF474747)
val darkSurfaceVariant = Color(0xFF474747)
val darkSurfaceContainerHigh = Color(0xFF2A2A2A)
val darkOnError = Color(0xFF601410)
val darkOnBackground = Color(0xFFE2E2E2)
val darkOnSurface = Color(0xFFE2E2E2)
val darkSurfaceContainer = Color(0xFF1F1F1F)
val darkInverseSurface = Color(0xFFE2E2E2)
val darkSurface = Color(0xFF131313)
val darkSurfaceContainerHighest = Color(0xFF353535)
val darkSurfaceBright = Color(0xFF393939)
val darkOnPrimary = Color(0xFF003060)
val darkPrimary = Color(0xFFA4C9FF)
val darkOnTertiary = Color(0xFF3D2846)
val darkOnSecondaryContainer = Color(0xFFD8E3F8)
val darkInverseOnSurface = Color(0xFF303030)
val darkOnErrorContainer = Color(0xFFF9DEDC)
val darkOnPrimaryContainer = Color(0xFFD3E3FF)
val darkPrimaryContainer = Color(0xFF17487D)
val darkSecondary = Color(0xFFBCC7DB)
val darkTertiaryContainer = Color(0xFF553F5D)
val darkSurfaceTint = Color(0xFFA4C9FF)
val darkSecondaryContainer = Color(0xFF3D4758)
val darkBackground = Color(0xFF131313)
val darkTertiary = Color(0xFFDABCE2)
val darkScrim = Color(0xFF000000)
val darkError = Color(0xFFF2B8B5)
val darkSurfaceContainerLowest = Color(0xFF0E0E0E)
val darkOutline = Color(0xFF919191)
val darkOnSecondary = Color(0xFF263141)
val darkOnSurfaceVariant = Color(0xFFC6C6C6)
val darkInversePrimary = Color(0xFF355F97)
val darkErrorContainer = Color(0xFF8C1D18)
val darkSurfaceContainerLow = Color(0xFF1B1B1B)
val darkSurfaceDim = Color(0xFF131313)


// Light Theme
val lightOnTertiaryContainer = Color(0xFF271430)
val lightOutlineVariant = Color(0xFFC6C6C6)
val lightSurfaceVariant = Color(0xFFE2E2E2)
val lightSurfaceContainerHigh = Color(0xFFE8E8E8)
val lightOnError = Color(0xFFFFFFFF)
val lightOnBackground = Color(0xFF1B1B1B)
val lightOnSurface = Color(0xFF1B1B1B)
val lightSurfaceContainer = Color(0xFFEEEEEE)
val lightInverseSurface = Color(0xFF303030)
val lightSurface = Color(0xFFF9F9F9)
val lightSurfaceContainerHighest = Color(0xFFE2E2E2)
val lightSurfaceBright = Color(0xFFF9F9F9)
val lightOnPrimary = Color(0xFFFFFFFF)
val lightPrimary = Color(0xFF355F97)
val lightOnTertiary = Color(0xFFFFFFFF)
val lightOnSecondaryContainer = Color(0xFF121C2B)
val lightInverseOnSurface = Color(0xFFF1F1F1)
val lightOnErrorContainer = Color(0xFF410E0B)
val lightOnPrimaryContainer = Color(0xFF001C3B)
val lightPrimaryContainer = Color(0xFFD3E3FF)
val lightSecondary = Color(0xFF545F70)
val lightTertiaryContainer = Color(0xFFF7D9FF)
val lightSurfaceTint = Color(0xFF355F97)
val lightSecondaryContainer = Color(0xFFD8E3F8)
val lightBackground = Color(0xFFF9F9F9)
val lightTertiary = Color(0xFF6E5676)
val lightScrim = Color(0xFF000000)
val lightError = Color(0xFFB3261E)
val lightSurfaceContainerLowest = Color(0xFFFFFFFF)
val lightOutline = Color(0xFF767676)
val lightOnSecondary = Color(0xFFFFFFFF)
val lightOnSurfaceVariant = Color(0xFF474747)
val lightInversePrimary = Color(0xFFA4C9FF)
val lightErrorContainer = Color(0xFFF9DEDC)
val lightSurfaceContainerLow = Color(0xFFF3F3F3)
val lightSurfaceDim = Color(0xFFDADADA)


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


