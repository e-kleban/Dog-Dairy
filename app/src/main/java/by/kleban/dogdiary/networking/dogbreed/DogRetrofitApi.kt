package by.kleban.dogdiary.networking.dogbreed

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.entities.DogBreed
import by.kleban.dogdiary.networking.entities.response.DogBreedResponse
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