package by.kleban.dogdairy.repositories

import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogBreed


interface DogRepository {

    suspend fun loadBreeds(): List<DogBreed>

    suspend fun getAllDog(): List<Dog>

    suspend fun saveDog(dog: Dog)
}