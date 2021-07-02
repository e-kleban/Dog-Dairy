package by.kleban.dogdairy.repositories

import by.kleban.dogdairy.entities.DogFact


interface DogFactsRepository {

    suspend fun loadRandomFact(): DogFact
}