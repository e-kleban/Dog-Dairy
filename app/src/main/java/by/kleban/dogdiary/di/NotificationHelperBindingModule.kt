package by.kleban.dogdiary.di

import by.kleban.dogdiary.helper.NotificationHelper
import by.kleban.dogdiary.helper.NotificationHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationHelperBindingModule {

    @Binds
    abstract fun bindNotificationHelper(notificationHelperImpl: NotificationHelperImpl): NotificationHelper
}