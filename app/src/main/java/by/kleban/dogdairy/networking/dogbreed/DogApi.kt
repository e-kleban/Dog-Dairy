package by.kleban.dogdairy.networking.dogbreed

import by.kleban.dogdairy.entities.DogBreed


interface DogApi {
    suspend fun loadBreeds(): List<DogBreed>
}