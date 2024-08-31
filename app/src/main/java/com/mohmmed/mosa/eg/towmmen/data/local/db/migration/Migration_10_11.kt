package com.mohmmed.mosa.eg.towmmen.data.local.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_10_11 = object: Migration(10, 11){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE INDEX index_purchase_items_purchaseId ON purchase_items(purchaseId)")
        db.execSQL("CREATE INDEX index_invoice_items_invoiceId ON invoice_items(invoiceId)")
        db.execSQL("ALTER TABLE  'invoices' ADD COLUMN 'profit' REAL NOT NULL DEFAULT 0.0")
        db.execSQL("ALTER TABLE  'invoice_items' ADD COLUMN 'unitCost' REAL NOT NULL DEFAULT 0.0")

    }

}