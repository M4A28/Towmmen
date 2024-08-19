package com.mohmmed.mosa.eg.towmmen.data.local.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_6_7 = object: Migration(6, 7){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS dealers (`dealerId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " `dealerName` TEXT NOT NULL," + " `createDate` INTEGER NOT NULL, `dealerPhoneNumber` TEXT NOT NULL," +
                " `dealerAddress` TEXT NOT NULL,  `dealerNote` TEXT NOT NULL)")
    }

}