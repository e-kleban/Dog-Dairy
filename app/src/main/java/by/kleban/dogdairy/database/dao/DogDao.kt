package by.kleban.dogdairy.database.dao

import androidx.room.Query
import by.kleban.dogdairy.database.entities.DbDog


interface DogDao {

    @Query("SELECT * FROM dog_table")
    suspend fun getAllDog():List<DbDog>
}