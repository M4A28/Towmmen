package com.mohmmed.mosa.eg.towmmen.data.localusermanger

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.mohmmed.mosa.eg.towmmen.domin.localusermanger.LockerSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LockerSettingRepositoryImp @Inject constructor(private val dataStore: DataStore<Preferences>)
    : LockerSettingRepository {
    private val SELL_KEY = booleanPreferencesKey("SELL_KEY")
    private val BUY_KEY = booleanPreferencesKey("BUY_KEY")
    private val EXPANSE_KEY = booleanPreferencesKey("EXPANSE_KEY")
    override suspend fun setCanAddSellToDb(canSave: Boolean) {
        dataStore.edit { preferences ->
            preferences[SELL_KEY] = canSave
        }
    }

    override fun getCanAddSellToDb(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[SELL_KEY] ?: true
        }
    }

    override suspend fun setCanAddBuyToDb(canSave: Boolean) {
        dataStore.edit { preferences ->
            preferences[BUY_KEY] = canSave
        }
    }

    override fun getCanAddBuyToDb(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[BUY_KEY] ?: true
        }
    }

    override suspend fun setCanSaveExpanseToDb(canSave: Boolean) {
        dataStore.edit { preferences ->
            preferences[EXPANSE_KEY] = canSave
        }
    }

    override fun getCanSaveExpanseToDb(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[EXPANSE_KEY] ?: true
        }
    }
}