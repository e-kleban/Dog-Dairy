package by.kleban.dogdiary.networking.dogbreed

import by.kleban.dogdiary.entities.DogBreed


interface DogApi {
    suspend fun loadBreeds(): List<DogBreed>
}