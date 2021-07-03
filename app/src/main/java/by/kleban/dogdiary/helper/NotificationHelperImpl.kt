package by.kleban.dogdiary.helper

import android.content.Context
import androidx.core.app.NotificationCompat
import by.kleban.dogdiary.DogDiaryApplication.Companion.CHANNEL_ID
import by.kleban.dogdiary.R
import by.kleban.dogdiary.entities.DogFact
import javax.inject.Inject


class NotificationHelperImpl @Inject constructor() : NotificationHelper {

    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_TAG = "NotificationHelper"
    }

    override fun createNotification(fact: DogFact, context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_peekaboo_cute_dog_stroke_by_vexels)
            .setContentTitle("New fact")
            .setContentText(fact.fact)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle())
    }
}