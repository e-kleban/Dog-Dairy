package by.kleban.dogdiary.di

import android.content.Context
import androidx.room.Room
import by.kleban.dogdiary.database.DogDiaryRoomDatabase
import by.kleban.dogdiary.database.dao.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDogDiaryRoomDatabase(@ApplicationContext context: Context): DogDiaryRoomDatabase {
        return Room.databaseBuilder(
            context,
            DogDiaryRoomDatabase::class.java,
            "database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDogDao(db: DogDiaryRoomDatabase): DogDao {
        return db.getDogDao()
    }

}