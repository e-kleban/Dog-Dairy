package by.kleban.dogdairy.repositories

import android.content.Context
import by.kleban.dogdairy.database.DogDb
import by.kleban.dogdairy.database.DogDiaryRoomDatabase
import by.kleban.dogdairy.database.DogRoomDb
import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.database.mapper.DbDogMapper
import by.kleban.dogdairy.database.mapper.DogMapper
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogBreed
import by.kleban.dogdairy.networking.dogbreed.DogApi
import by.kleban.dogdairy.networking.dogbreed.DogRetrofitApi
import by.kleban.dogdairy.networking.mappers.DogBreedResponseMapper
import javax.inject.Inject


class DogRepositoryImpl @Inject constructor(
    private val dogDb: DogDb,
    private val dogApi: DogApi
) : DogRepository {

    override suspend fun loadBreeds(): List<DogBreed> {
        return dogApi.loadBreeds()
    }

    override suspend fun getDog(): Dog? {
        val dogList = dogDb.getAllDog()
        return if (dogList.isNotEmpty()) {
            dogList.first()
        } else {
            return null
        }
    }

    override suspend fun saveDog(dog: Dog): Long {
        return dogDb.saveDog(dog)
    }

    override suspend fun getDogWithPosts(id: Long): DbDogWithPosts {
        return dogDb.getDogWithPosts(id)
    }
}