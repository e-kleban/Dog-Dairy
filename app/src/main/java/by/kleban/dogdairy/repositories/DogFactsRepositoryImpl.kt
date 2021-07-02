package by.kleban.dogdairy.repositories

import by.kleban.dogdairy.entities.DogFact
import by.kleban.dogdairy.networking.dogfacts.DogFactsApi
import javax.inject.Inject
import kotlin.random.Random

class DogFactsRepositoryImpl @Inject constructor(
    private val dogFactsApi: DogFactsApi
) : DogFactsRepository {

    override suspend fun loadRandomFact(): DogFact {
        val facts = dogFactsApi.loadFacts()
        val indexRandomFact = Random.nextInt(0, facts.size)
        return facts[indexRandomFact]
    }
}