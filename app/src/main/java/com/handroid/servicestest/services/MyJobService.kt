package com.handroid.servicestest.services

import  android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(parameters: JobParameters?): Boolean {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer $i")
            }
            jobFinished(parameters,true)
        }
        return true
    }

    override fun onStopJob(parameters: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d(SERVICE_TAG, "MyJobService: $message")
    }

    companion object {
        const val SERVICE_TAG = "SERVICE_TAG"
        private const val NAME = "MyJobService"
        const val JOB_ID = 1
    }
}