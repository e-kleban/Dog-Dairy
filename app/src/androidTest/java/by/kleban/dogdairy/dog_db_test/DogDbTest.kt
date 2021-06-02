package by.kleban.dogdairy.dog_db_test

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import by.kleban.dogdairy.database.DogDiaryRoomDatabase
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.repositories.DogRepository
import by.kleban.dogdairy.repositories.DogRepositoryImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class DogDbTest {

    private lateinit var dogDao: DogDao
    private lateinit var repository: DogRepository

    @Before
    fun init() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db =
            Room.inMemoryDatabaseBuilder(context, DogDiaryRoomDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dogDao = db.getDogDao()
        repository = DogRepositoryImpl.getDogBreedRepository(context)
    }

    @Test
    fun saveDog_dogInserted() {
        val dog = Dog(
            name = "Dolka",
            image = "uri",
            age = 4,
            sex = "female",
            breed = "Poodle",
            description = "The best dog"
        )

        runBlocking {
            repository.saveDog(dog)
            val dbDog = repository.getDog()
            if (dbDog != null)
                Truth.assertThat(
                    dbDog.age == dog.age &&
                            dbDog.name == dog.name &&
                            dbDog.breed == dog.breed &&
                            dbDog.sex == dog.description &&
                            dbDog.image == dog.image &&
                            dbDog.description == dog.description
                ).isTrue()
        }
    }

    @Test
    fun getDog_emptyList() {
        runBlocking {
            val dbDog = dogDao.getAllDog().first()
            Log.e("test_dog", dbDog.toString())
        }
    }
}