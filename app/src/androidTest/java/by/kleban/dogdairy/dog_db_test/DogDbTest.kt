package by.kleban.dogdairy.dog_db_test

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import by.kleban.dogdairy.database.DogDiaryRoomDatabase
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.entities.Dog
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
}