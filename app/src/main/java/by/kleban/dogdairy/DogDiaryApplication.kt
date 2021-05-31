package by.kleban.dogdairy

import android.app.Application


class DogDiaryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private lateinit var _instance: Application
        val instance: Application
            get() = _instance
    }
}