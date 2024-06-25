package com.kingfu.clok.settings.viewModel.settingsViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingfu.clok.settings.repository.SettingsPreferences

class SettingsFactory(
    private val settingsPreferences: SettingsPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                settingsPreferences =  settingsPreferences,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}