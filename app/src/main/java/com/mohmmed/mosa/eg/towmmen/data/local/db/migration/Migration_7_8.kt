package com.mohmmed.mosa.eg.towmmen.data.local.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val migration_7_8 = object: Migration(7, 8){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS locker ("
                + "`transActonId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + " `transActionType` TEXT NOT NULL,"
                + " `transActionDate` INTEGER NOT NULL,"
                + " `transActionAmount` REAL NOT NULL )"
        )
    }

}