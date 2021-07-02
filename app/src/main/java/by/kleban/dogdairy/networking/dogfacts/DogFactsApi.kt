package by.kleban.dogdairy.networking.dogfacts

import by.kleban.dogdairy.entities.DogFact


interface DogFactsApi {

    suspend fun loadFacts(): List<DogFact>
}