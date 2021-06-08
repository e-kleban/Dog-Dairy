package by.kleban.dogdairy.dog_db_test

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import by.kleban.dogdairy.database.DogDiaryRoomDatabase
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbPost
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class DogDbTest {

    private lateinit var dogDao: DogDao

    @Before
    fun init() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db =
            Room.inMemoryDatabaseBuilder(context, DogDiaryRoomDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dogDao = db.getDogDao()
    }

    @Test
    fun savePost_isDeletedDogWithPosts() {
        runBlocking {
            val dbDog1 = DbDog("1Dolka", "ref", 5, 1, "Poodle", "the best")
            val dbDog2 = DbDog("2Dolkdfa", "ref", 5, 2, "Poodle", "the best")
            val dbDog3 = DbDog("3Dolkdfa", "ref", 5, 1, "Poodle", "the best")
            val idDog1 = dogDao.saveDog(dbDog1)
            val idDog2 = dogDao.saveDog(dbDog2)
            val idDog3 = dogDao.saveDog(dbDog3)

            val post1 = DbPost(idDog1, "1fefe", "defef")
            val post2 = DbPost(idDog2, "2fefe", "defef")
            val post3 = DbPost(idDog3, "3fefe", "defef")
            dogDao.savePost(post1)
            dogDao.savePost(post2)
            dogDao.savePost(post3)
            dogDao.deleteDogWithPosts(idDog2)
            val listDog = dogDao.getAllDog()
            val listPosts = dogDao.getAllPosts()

            Truth.assertThat(listDog).doesNotContain(dbDog2)
            Truth.assertThat(listPosts).doesNotContain(post2)
        }
    }

}