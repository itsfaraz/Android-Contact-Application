package com.itsfrz.authentication.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.itsfrz.authentication.data.entities.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val PREFERENCE_NAME = "my_preference"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class UserPreferenceRepository(private val context: Context) {

    companion object {
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val LOGGED_IN_DATE = stringPreferencesKey("LOGGED_IN_DATE")
        val APP_COLOR_THEME = stringPreferencesKey("APP_COLOR_THEME")
        val CONTACT_SORTING = stringPreferencesKey("CONTACT_SORTING")
        val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
    }

    suspend fun saveUserPref(userPreferences: UserPreferences) {
        context.datastore.edit { preferences ->
            preferences[USER_NAME] = userPreferences.username
            preferences[LOGGED_IN_DATE] = userPreferences.loggedInDate
            preferences[APP_COLOR_THEME] = userPreferences.appTheme
            preferences[CONTACT_SORTING] = userPreferences.contactSortingType
            preferences[IS_LOGGED_IN] = userPreferences.isLoggedIn
        }
    }

    fun getUserPref(): Flow<UserPreferences> = context.datastore.data.map { userPref ->
        UserPreferences(
            username = userPref[USER_NAME] ?: "",
            loggedInDate = userPref[LOGGED_IN_DATE] ?: "",
            contactSortingType = userPref[CONTACT_SORTING] ?: "",
            appTheme = userPref[APP_COLOR_THEME] ?: "",
            isLoggedIn = userPref[IS_LOGGED_IN] ?: false
        )
    }

}
