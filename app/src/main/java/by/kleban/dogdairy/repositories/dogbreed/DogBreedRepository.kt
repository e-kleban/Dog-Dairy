package by.kleban.dogdairy.repositories.dogbreed

import by.kleban.dogdairy.data.entities.dogbreeds.DogBreed
import by.kleban.dogdairy.mappers.dogbreed.DogBreedMapper
import by.kleban.dogdairy.networking.dogbreed.DogBreedServiceProvider
import by.kleban.dogdairy.networking.dogbreed.IDogBreedService


class DogBreedRepository(private val dogBreedService: IDogBreedService): IDogBreedRepository {

    private val dogBreedMapper = DogBreedMapper()
//    private val dogBreedService = DogBreedServiceProvider.provideDogBreedService()

    companion object {
        private var INSTANCE: DogBreedRepository? = null
        fun getDogBreedRepository(): DogBreedRepository {
            return if (INSTANCE == null) {
                INSTANCE = DogBreedRepository(DogBreedServiceProvider.provideDogBreedService())
                INSTANCE as DogBreedRepository
            } else {
                INSTANCE as DogBreedRepository
            }
        }
    }

    override suspend fun loadBreeds(): List<DogBreed> {
        val response = dogBreedService.loadBreeds()
        return if (response.isSuccessful) {
            response.body()
                ?.map { dogBreedMapper.map(it) }
                .orEmpty()
        } else {
            throw Throwable(response.errorBody().toString())
        }
    }
}