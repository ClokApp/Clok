package com.kingfu.clok.repository.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsPreferences private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        fun getInstance(context: Context): SettingsPreferences {
            return INSTANCE ?: synchronized(lock = this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = SettingsPreferences(context = context)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settingsDataStore")
    private val settingsDataStore: DataStore<Preferences> = context.settingsDataStore
    private val _appTheme = stringPreferencesKey(name = "appTheme")

    /************************************************ Clear ************************************************/


    /************************************************ set ************************************************/

    suspend fun setAppTheme(string: String) {
        settingsDataStore.edit { preferences ->
            preferences[_appTheme] = string
        }
    }

    /************************************************ get ************************************************/

    val getAppTheme: Flow<String> = settingsDataStore.data
        .map { preferences ->
            preferences[_appTheme] ?: ThemeType.Dark.name
        }

}