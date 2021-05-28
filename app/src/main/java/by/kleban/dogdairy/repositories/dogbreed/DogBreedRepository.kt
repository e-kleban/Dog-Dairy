package by.kleban.dogdairy.repositories.dogbreed

import by.kleban.dogdairy.data.dto.dogbreeds.DogBreedResponse
import by.kleban.dogdairy.data.entities.dogbreeds.DogBreed
import by.kleban.dogdairy.mappers.Mapper
import by.kleban.dogdairy.mappers.dogbreed.DogBreedMapper
import by.kleban.dogdairy.networking.dogbreed.DogBreedServiceProvider
import by.kleban.dogdairy.networking.dogbreed.IDogBreedService


class DogBreedRepository(
    private val dogBreedService: IDogBreedService,
    private val dogBreedMapper: Mapper<DogBreedResponse, DogBreed>,
) : IDogBreedRepository {

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

    companion object {
        private var INSTANCE: DogBreedRepository? = null

        fun getDogBreedRepository(): DogBreedRepository {
            return if (INSTANCE == null) {
                INSTANCE = DogBreedRepository(
                    DogBreedServiceProvider.provideDogBreedService(),
                    DogBreedMapper()
                )
                INSTANCE as DogBreedRepository
            } else {
                INSTANCE as DogBreedRepository
            }
        }
    }
}