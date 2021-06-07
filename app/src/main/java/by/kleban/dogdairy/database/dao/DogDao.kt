package by.kleban.dogdairy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbDogWithPosts

@Dao
interface DogDao {

    @Query("SELECT * FROM table_dog")
    suspend fun getAllDog(): List<DbDog>

    @Insert
    suspend fun saveDog(dogDb: DbDog)

    @Transaction
    @Query("SELECT * FROM table_dog")
    suspend fun getDogWithPosts():List<DbDogWithPosts>
}