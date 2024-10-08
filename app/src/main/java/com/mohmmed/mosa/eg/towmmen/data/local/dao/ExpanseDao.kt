package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerPeriod
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExpanseDao {

    @Upsert
    suspend fun upsertExpanse(expanse: Expanse)

    @Delete
    suspend fun deleteExpanse(expanse:Expanse)


    @Query("SELECT * FROM expanse ORDER BY payDate DESC")
    fun getAllExpanse(): Flow<List<Expanse>>

    @Query("SELECT * FROM expanse WHERE payDate BETWEEN :start AND :end")
    fun getExpanseInRange(start: Date, end: Date): Flow<List<Expanse>>

    @Query("SELECT strftime('%Y-%m', payDate/1000, 'unixepoch') as period, " +
            " SUM(amount) as total FROM expanse GROUP BY period ORDER BY period")
    fun getExpansePerMonth():Flow<List<ExpansePerPeriod>>

    @Query("SELECT strftime('%Y-%m-%d', payDate/1000, 'unixepoch') as period, " +
            " SUM(amount) as total FROM expanse GROUP BY period ORDER BY period")
    fun getExpansePerDay():Flow<List<ExpansePerPeriod>>
    @Query("SELECT strftime('%Y-%m-%d', payDate/1000, 'unixepoch') as period, " +
            " SUM(amount) as total FROM expanse GROUP BY strftime('%Y-%m-%W', payDate/1000, 'unixepoch') ORDER BY period")
    fun getExpansePerWeek():Flow<List<ExpansePerPeriod>>



    @Query("SELECT AVG(amount) FROM expanse")
    fun getAvgExpanse(): Flow<Double?>

    @Query("DELETE FROM expanse")
    suspend fun clearExpanseDate()

    @Query("SELECT AVG(total) " +
            "FROM (SELECT strftime('%Y-%m', payDate/1000, 'unixepoch') as month, " +
            "SUM(amount) as total FROM expanse GROUP BY month ORDER BY month )")
    fun getAvgExpansePerMonth(): Flow<Double?>

}