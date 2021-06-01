package by.kleban.dogdairy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.kleban.dogdairy.database.entities.DbDog

@Dao
interface DogDao {

    @Query("SELECT * FROM dog_table")
    suspend fun getAllDog(): List<DbDog>

    @Insert
    suspend fun saveDog(dogDb: DbDog)
}