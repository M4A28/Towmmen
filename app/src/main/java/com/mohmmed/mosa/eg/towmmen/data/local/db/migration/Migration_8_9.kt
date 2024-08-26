package com.mohmmed.mosa.eg.towmmen.data.local.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val migration_8_9 = object: Migration(8, 9){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(" ALTER TABLE locker ADD COLUMN 'transActionNote' TEXT NOT NULL DEFAULT ' '")
    }

}