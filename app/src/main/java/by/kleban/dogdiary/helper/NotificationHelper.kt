package by.kleban.dogdiary.helper

import android.content.Context
import androidx.core.app.NotificationCompat
import by.kleban.dogdiary.entities.DogFact


interface NotificationHelper {

    fun createNotification(fact: DogFact, context: Context): NotificationCompat.Builder
}