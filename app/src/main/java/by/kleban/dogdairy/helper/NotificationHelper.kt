package by.kleban.dogdairy.helper

import android.content.Context
import androidx.core.app.NotificationCompat
import by.kleban.dogdairy.entities.DogFact


interface NotificationHelper {

    fun createNotification(fact: DogFact, context: Context): NotificationCompat.Builder
}