package com.mohmmed.mosa.eg.towmmen.util

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File
import java.util.Date


fun backupDatabase(context: Context) {
    val createDate = dateToString(Date(),"yyyy-MM-dd")
    val parentDir = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) APP_DIR_R
    else APP_DIR


    val currentDayDir = File(parentDir.path + "/${createDate}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val dbFile = context.getDatabasePath(DB_NAME)
    val backupFile = File(currentDayDir, "$DB_NAME $createDate.db")

    dbFile.inputStream().use { input ->
        backupFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}

fun restoreDatabase(context: Context, backUpDbPath: String) {
    val dbFile = context.getDatabasePath(DB_NAME)
    val backupFile = File(Environment.getExternalStorageDirectory().path , backUpDbPath.split(":")[1])

    if (backupFile.exists()) {
        if(context.deleteDatabase(DB_NAME)){
            backupFile.inputStream().use { input ->
                dbFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

    }
}
