package com.mohmmed.mosa.eg.towmmen.data.localusermanger

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mohmmed.mosa.eg.towmmen.domin.localusermanger.LocalUserManger
import com.mohmmed.mosa.eg.towmmen.util.APP_ENTRY_KEY
import com.mohmmed.mosa.eg.towmmen.util.USER_SETTING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserMangerImpl(
    private val context: Context
): LocalUserManger {

    override suspend fun saveAppEntry() {
        context.dataStore.edit { setting ->
            setting[preferencesKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { pre ->
            pre[preferencesKeys.APP_ENTRY]?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTING)

private object preferencesKeys{
    val APP_ENTRY = booleanPreferencesKey(name = APP_ENTRY_KEY)
}