package by.kleban.dogdairy.networking.dogbreed

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.DogBreed
import by.kleban.dogdairy.networking.entities.response.DogBreedResponse
import javax.inject.Inject

class DogRetrofitApi @Inject constructor(
    private val dogBreedService: DogBreedService,
    private val dogBreedMapper: Mapper<DogBreedResponse, DogBreed>
) : DogApi {

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