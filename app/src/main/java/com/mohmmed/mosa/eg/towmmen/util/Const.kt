package com.mohmmed.mosa.eg.towmmen.util

import android.os.Environment
import java.io.File


const val ONE_HOUR = 1000L * 60L * 60L

const val ONE_DAY = ONE_HOUR * 24L

const val ONE_WEEK = ONE_DAY * 7L

const val TOW_WEEKS = ONE_WEEK * 2L

const val ONE_MONTH = ONE_DAY * 30L

const val ONE_YEAR = ONE_DAY * 365L

const val PRODUCT_KEY = "product_key"

const val CUSTOMER_KEY = "customer_key"

const val EXPANSE_KEY = "expanse_key"

const val CUSTOMER_ID = "customer_id"

const val NOTE_KEY = "note_key"

const val DEALER_KEY = "dealer_key"

const val DEALER_ID = "dealer_id"

const val SCANNED_BARCODE = "scannedBarcode"

const val APP_ENTRY_KEY = "app_entry"

const val DB_NAME = "towwmen_db"

const val INVOICE_KEY = "invoice_key"

const val INVOICE_ID = "invoice_id"

const val USER_SETTING = "USER_SETTING"

const val DB_WORK = "DB_WORK"

const val EXP_PRODUCT_WORK = "EXP_PRODUCT_WORK"

val APP_DIR_R = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + "/Shop_Manger")

val APP_DIR =   File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")

