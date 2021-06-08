package by.kleban.dogdairy.dog_db_test

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import by.kleban.dogdairy.database.DogDiaryRoomDatabase
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbPost
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
    fun savePost() {
        runBlocking {
            val l = dogDao.saveDog(DbDog("1Dolka", "ref", 5, "female", "Poodle", "the best"))
            val s = dogDao.saveDog(DbDog("2Dolkdfa", "ref", 5, "female", "Poodle", "the best"))
            val third = dogDao.saveDog(DbDog("3Dolkdfa", "ref", 5, "female", "Poodle", "the best"))

            dogDao.savePost(DbPost(l, "1fefe", "defef"))
            dogDao.savePost(DbPost(s, "2fefe", "defef"))
            dogDao.savePost(DbPost(third, "3fefe", "defef"))
            dogDao.deleteDogWithPosts(s)
            val list = dogDao.getAllDog()
            val new = dogDao.getAllPosts()
            list.forEach {  Log.e("Hello", it.toString()) }
            new.forEach {  Log.e("Hello", it.toString()) }


        }
    }

}