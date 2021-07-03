package by.kleban.dogdiary.networking.dogfacts

import by.kleban.dogdiary.entities.DogFact


interface DogFactsApi {

    suspend fun loadFacts(): List<DogFact>
}