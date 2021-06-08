package by.kleban.dogdairy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbPost

@Database(entities = [DbDog::class, DbPost::class], version = 1)
abstract class DogDiaryRoomDatabase : RoomDatabase() {

    abstract fun getDogDao(): DogDao

    companion object {
        var INSTANCE: DogDiaryRoomDatabase? = null
        fun getDogDb(context: Context): DogDiaryRoomDatabase {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, DogDiaryRoomDatabase::class.java, "database")
                    .build()
                INSTANCE as DogDiaryRoomDatabase
            } else {
                INSTANCE as DogDiaryRoomDatabase
            }
        }
    }
}