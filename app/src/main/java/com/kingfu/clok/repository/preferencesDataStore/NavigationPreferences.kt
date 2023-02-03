package com.kingfu.clok.repository.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NavigationPreferences private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: NavigationPreferences? = null

        fun getInstance(context: Context): NavigationPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = NavigationPreferences(context)
                INSTANCE = instance
                return instance
            }
        }
    }

    private val Context.navigationDataStore: DataStore<Preferences> by preferencesDataStore(name = "navigationDataStore")
    private val navigationDataStore: DataStore<Preferences> = context.navigationDataStore

    private val startDestinationDefault = "stopwatch"

    private val _startDestination = stringPreferencesKey("startDestination")

    /************************************************ Clear ************************************************/


    /************************************************ set ************************************************/

    suspend fun setStartDestination(string: String){
        navigationDataStore.edit { preferences ->
            preferences[_startDestination] = string
        }
    }

    /************************************************ get ************************************************/

    val getStartDestination: Flow<String> = navigationDataStore.data
        .map { preferences ->
            preferences[_startDestination] ?: startDestinationDefault
        }

}