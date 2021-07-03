package by.kleban.dogdiary.networking.dogfacts

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.entities.DogFact
import by.kleban.dogdiary.networking.entities.response.DogFactResponse
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