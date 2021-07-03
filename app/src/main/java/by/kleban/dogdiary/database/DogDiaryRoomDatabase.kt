package by.kleban.dogdiary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.kleban.dogdiary.database.dao.DogDao
import by.kleban.dogdiary.database.entities.DbDog
import by.kleban.dogdiary.database.entities.DbPost

@Database(entities = [DbDog::class, DbPost::class], version = 1)
abstract class DogDiaryRoomDatabase : RoomDatabase() {

    abstract fun getDogDao(): DogDao

}