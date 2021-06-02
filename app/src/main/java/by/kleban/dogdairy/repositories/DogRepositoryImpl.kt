package by.kleban.dogdairy.repositories

import android.content.Context
import by.kleban.dogdairy.database.DogDb
import by.kleban.dogdairy.database.DogDiaryRoomDatabase
import by.kleban.dogdairy.database.DogRoomDb
import by.kleban.dogdairy.database.mapper.DbDogMapper
import by.kleban.dogdairy.database.mapper.DogMapper
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogBreed
import by.kleban.dogdairy.networking.dogbreed.DogApi
import by.kleban.dogdairy.networking.dogbreed.DogBreedServiceProvider
import by.kleban.dogdairy.networking.dogbreed.DogRetrofitApi
import by.kleban.dogdairy.networking.mappers.DogBreedResponseMapper


class DogRepositoryImpl(
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

    override suspend fun saveDog(dog: Dog) {
        dogDb.saveDog(dog)
    }

    companion object {
        private var INSTANCE: DogRepositoryImpl? = null

        fun getDogBreedRepository(context: Context): DogRepositoryImpl {
            return if (INSTANCE == null) {
                val dogDao = DogDiaryRoomDatabase.getDogDb(context).getDogDao()
                val dogDb = DogRoomDb(dogDao, DbDogMapper(), DogMapper())
                val dogBreedService = DogBreedServiceProvider.provideDogBreedService()
                val dogApi = DogRetrofitApi(dogBreedService, DogBreedResponseMapper())
                INSTANCE = DogRepositoryImpl(dogDb, dogApi)
                INSTANCE as DogRepositoryImpl
            } else {
                INSTANCE as DogRepositoryImpl
            }
        }
    }
}