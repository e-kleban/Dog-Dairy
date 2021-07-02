package by.kleban.dogdairy.workmanager

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import by.kleban.dogdairy.helper.NotificationHelper
import by.kleban.dogdairy.helper.NotificationHelperImpl
import by.kleban.dogdairy.repositories.DogFactsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class LoadWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: DogFactsRepository,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val dogFact = repository.loadRandomFact()
            withContext(Dispatchers.Main) {
                val builder = notificationHelper.createNotification(dogFact, appContext)
                with(NotificationManagerCompat.from(appContext)) {
                    notify(
                        NotificationHelperImpl.NOTIFICATION_TAG,
                        NotificationHelperImpl.NOTIFICATION_ID,
                        builder.build()
                    )
                }
            }
            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure()
        }
    }
}