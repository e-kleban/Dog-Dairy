package by.kleban.dogdiary.repositories

import by.kleban.dogdiary.entities.DogFact


interface DogFactsRepository {

    suspend fun loadRandomFact(): DogFact
}