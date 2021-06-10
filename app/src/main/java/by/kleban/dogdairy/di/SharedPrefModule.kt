package by.kleban.dogdairy.di

import android.content.Context
import android.content.SharedPreferences
import by.kleban.dogdairy.entities.SharedConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class SharedPrefModule {

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedConfig.NAME_SHARED_PREF, Context.MODE_PRIVATE)
    }
}