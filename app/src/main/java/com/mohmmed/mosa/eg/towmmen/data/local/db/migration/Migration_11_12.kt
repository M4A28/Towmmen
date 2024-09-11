package com.mohmmed.mosa.eg.towmmen.data.local.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val migration_11_12 = object: Migration(11, 12) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL(" CREATE TABLE IF NOT EXISTS `debts` ( " +
                " `debtId` TEXT PRIMARY KEY  NOT NULL," +
                " `debtAmount` REAL NOT NULL, " +
                " `customerId` INTEGER NOT NULL," +
                " `customerName` TEXT NOT NULL," +
                " `debtDate` INTEGER NOT NULL, " +
                " `isPayed` INTEGER NOT NULL, " +
                " `payDate` INTEGER ) ")

        db.execSQL(" CREATE TABLE IF NOT EXISTS `debts_items` ( " +
                " `debtItemId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " `debtId` TEXT NOT NULL," +
                " `productId` INTEGER NOT NULL, " +
                " `productName` TEXT NOT NULL," +
                " `unitPrice` REAL NOT NULL, " +
                " `quantity` INTEGER NOT NULL, " +
                " `date` INTEGER , " +
                "  FOREIGN KEY(`debtId`) " +
                "  REFERENCES `debts`(`debtId`) " +
                " ON UPDATE NO ACTION ON DELETE CASCADE  ) ")

        db.execSQL("CREATE INDEX index_debts_items_debtId ON debts_items(debtId)")

    }
}