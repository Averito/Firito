package com.averito.firito.data.workers.daylog

import android.content.Context
import androidx.work.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object DayLogWorkerScheduler {
    private fun calculateInitialDelayToTime(hour: Int = 23, minute: Int = 59): Long {
        val now = LocalDateTime.now()
        val targetTime = now.toLocalDate().atTime(hour, minute)
        val nextRun = if (now.isBefore(targetTime)) targetTime else targetTime.plusDays(1)
        return Duration.between(now, nextRun).toMillis()
    }

    fun schedule(context: Context) {
        val delayMs = calculateInitialDelayToTime()

        val request = PeriodicWorkRequestBuilder<DayLogWorker>(
            1, TimeUnit.DAYS
        )
            .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "day_log_worker",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}
