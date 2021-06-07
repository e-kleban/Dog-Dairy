package by.kleban.dogdairy.dog_db_test

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import by.kleban.dogdairy.database.DogDb
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
            dogDao.saveDog(DbDog("Dolka","ref",5,"female","Poodle","the best"))
           val dog= dogDao.getAllDog().first()
            dogDao.savePost(DbPost(dog.id!!,"fefe","defef"))
           val list = dogDao.getDogWithPosts()
            Log.e("Hello",list.first().toString())

        }
    }

}