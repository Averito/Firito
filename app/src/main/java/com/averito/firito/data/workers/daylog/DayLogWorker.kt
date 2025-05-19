package com.averito.firito.data.workers.daylog

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.interactors.day_log.DayLogInteractorImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DayLogWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dayLogInteractor: DayLogInteractorImpl,
    @DefaultAppLogger private val defaultAppLogger: AppLogger
) : CoroutineWorker(context, workerParams) {
    private val name = "DayLogWorker"

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val createdDayLog = dayLogInteractor.finalizeDayLog()
            defaultAppLogger.info("$name: Создан новый лог - ${createdDayLog.date}")

            Result.success()
        } catch (error: Exception) {
            defaultAppLogger.error("$name: Ошибка при создании лога — ${error.message}")
            Result.failure()
        }
    }
}