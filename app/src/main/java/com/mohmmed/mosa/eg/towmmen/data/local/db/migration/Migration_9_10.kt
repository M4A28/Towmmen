package com.mohmmed.mosa.eg.towmmen.data.local.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_9_10 = object: Migration(9, 10){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `purchase_items` " +
                " (`itemId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " `purchaseId` TEXT NOT NULL, `productId` INTEGER NOT NULL, " +
                " `productName` TEXT NOT NULL, `quantity` INTEGER NOT NULL, " +
                " `unitPrice` REAL NOT NULL, `purchaseDate` INTEGER NOT NULL, " +
                " FOREIGN KEY(`purchaseId`) REFERENCES `purchases`(`purchaseId`) " +
                " ON UPDATE NO ACTION ON DELETE CASCADE )")

    }

}