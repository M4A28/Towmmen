package com.mohmmed.mosa.eg.towmmen.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mohmmed.mosa.eg.towmmen.util.backupDatabase

class BackUpDBWork (
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {
    private val context = context
    override fun doWork(): Result {
        try{
            backupDatabase(context)
            return Result.success()
        }catch (e: Exception){
            return Result.failure()
        }

    }

}