package by.kleban.dogdairy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbPost

@Database(entities = [DbDog::class, DbPost::class], version = 1)
abstract class DogDiaryRoomDatabase : RoomDatabase() {

    abstract fun getDogDao(): DogDao

}