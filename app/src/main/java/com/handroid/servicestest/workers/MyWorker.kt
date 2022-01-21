package com.handroid.servicestest.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.*

class MyWorker(
    context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i $page")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d(SERVICE_TAG, "$NAME: $message")
    }

    companion object {
        const val SERVICE_TAG = "SERVICE_TAG"
        private const val NAME = "MyWorker"
        private const val PAGE = "page"
        private const val JOB_ID = 1

        const val WORK_NAME = "work_name"

        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(workDataOf(PAGE to page))
                .setConstraints(makeConstrains())
                .build()
        }

        private fun makeConstrains(): Constraints {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Constraints.Builder()
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(NetworkType.TEMPORARILY_UNMETERED)
                    .build()
            } else {
                Constraints.Builder()
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .build()
            }
        }
    }
}