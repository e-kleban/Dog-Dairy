package by.kleban.dogdairy.repositories

import by.kleban.dogdairy.entities.DogBreed


interface DogRepository {

    suspend fun loadBreeds(): List<DogBreed>
}