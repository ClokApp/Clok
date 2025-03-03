package com.kingfu.clok.settings.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.navigation.AppDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsPreferences private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreferences? = null
        fun getInstance(context: Context): SettingsPreferences =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingsPreferences(context).also { INSTANCE = it }
            }
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingsDataStore")
    private val preferences: DataStore<Preferences> = context.dataStore
    private val theme = stringPreferencesKey(name = "theme")
    private val startRoute = stringPreferencesKey(name = "startRoute")

    /************************************************ set ************************************************/

    suspend fun setTheme(string: String) {
        preferences.edit { preferences ->
            preferences[theme] = string
        }
    }


    suspend fun setStartRoute(string: String) {
        preferences.edit { preferences ->
            preferences[startRoute] = string
        }
    }

    /************************************************ get ************************************************/

    val getTheme: Flow<String> = preferences.data
        .map { preferences ->
            preferences[theme] ?: ThemeType.LIGHT.name
        }

    val getStartRoute: Flow<String> = preferences.data
        .map { preferences ->
            preferences[startRoute] ?: AppDestination.STOPWATCH.name
        }



}