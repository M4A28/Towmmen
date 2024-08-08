package com.mohmmed.mosa.eg.towmmen.data.local.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val migration_5_6 = object: Migration(5, 6){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(" ALTER TABLE invoice_items ADD COLUMN 'purchaseDate' INTEGER NOT NULL DEFAULT 1000000")
        db.execSQL("CREATE TABLE IF NOT EXISTS expanse (`expanseId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " `expanse` TEXT NOT NULL," + " `payDate` INTEGER NOT NULL, `amount` REAL NOT NULL)")
    }

}