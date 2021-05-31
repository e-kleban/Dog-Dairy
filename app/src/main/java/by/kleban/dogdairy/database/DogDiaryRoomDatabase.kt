package by.kleban.dogdairy.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import by.kleban.dogdairy.database.dao.DogDao


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