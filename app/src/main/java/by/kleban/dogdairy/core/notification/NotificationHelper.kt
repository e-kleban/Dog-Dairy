package by.kleban.dogdairy.core.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import by.kleban.dogdairy.DogDiaryApplication.Companion.CHANNEL_ID
import by.kleban.dogdairy.R
import by.kleban.dogdairy.entities.DogFact
import javax.inject.Inject


class NotificationHelper @Inject constructor() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_TAG = "NotificationHelper"
    }

    fun createNotification(fact: DogFact, context: Context): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_add_24)
            .setContentTitle("New fact")
            .setContentText(fact.fact)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle())
        return builder
    }
}