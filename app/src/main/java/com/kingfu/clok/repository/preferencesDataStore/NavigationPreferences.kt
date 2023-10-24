package com.kingfu.clok.repository.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kingfu.clok.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NavigationPreferences private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: NavigationPreferences? = null

        fun getInstance(context: Context): NavigationPreferences {
            return INSTANCE ?: synchronized(lock = this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = NavigationPreferences(context = context)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private val Context.navigationDataStore: DataStore<Preferences> by preferencesDataStore(name = "navigationDataStore")
    private val navigationDataStore: DataStore<Preferences> = context.navigationDataStore
    private val _startRoute = stringPreferencesKey(name = "startRoute")

    /************************************************ Clear ************************************************/


    /************************************************ set ************************************************/

    suspend fun setStartRoute(string: String) {
        navigationDataStore.edit { preferences ->
            preferences[_startRoute] = string
        }
    }

    /************************************************ get ************************************************/

    val getStartRoute: Flow<String> = navigationDataStore.data
        .map { preferences ->
            preferences[_startRoute] ?: Screen.Stopwatch.route
        }

}