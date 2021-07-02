package by.kleban.dogdairy.networking.dogfacts

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.DogFact
import by.kleban.dogdairy.networking.entities.response.DogFactResponse
import javax.inject.Inject

class DogFactsApiImpl @Inject constructor(
    private val dogFactService: DogFactService,
    private val dogFactMapper: Mapper<DogFactResponse, DogFact>
) : DogFactsApi {
    override suspend fun loadFacts(): List<DogFact> {
        val response = dogFactService.loadFacts()
        return if (response.isSuccessful) {
            response.body()
                ?.map { dogFactMapper.map(it) }
                .orEmpty()
        } else {
            throw Throwable(response.errorBody().toString())
        }
    }
}