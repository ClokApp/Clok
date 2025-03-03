package com.kingfu.clok.settings.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.navigation.AppDestination
import com.kingfu.clok.navigation.topBar.TopBarBack
import com.kingfu.clok.settings.screen.components.PrivacyPolicy
import com.kingfu.clok.settings.screen.components.Theme
import com.kingfu.clok.settings.viewModel.SettingsState
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: SettingsState,
    topBar: @Composable () -> Unit,
    goToDialogTheme: (ThemeType) -> Unit,
) {

    Scaffold(
        containerColor = colorScheme.surface,
        modifier = modifier,
        topBar = { topBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues)
                        .verticalScroll(state = rememberScrollState())
                        .widthIn(max = 600.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.app),
                        style = typography.bodyLarge
                    )

                    Theme(
                        theme = state.theme,
                        goToDialogTheme = goToDialogTheme
                    )

                    Text(
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.about),
                        style = typography.bodyLarge
                    )

                    PrivacyPolicy()
                }
            }
        }
    )
}


//@PreviewLightDark
//@PreviewScreenSizes
@Preview
@Composable
private fun SettingsScreenPreview() {

    ClokThemePreview {
        SettingsScreen(
            state = SettingsState(
                theme = ThemeType.LIGHT
            ),
            topBar = {
                TopBarBack(
                    title = stringResource(id = AppDestination.SETTINGS.label),
                    onClick = { }
                )
            },
            goToDialogTheme = { },

        )
    }
}







