package by.kleban.dogdairy.repositories.dogbreed

import by.kleban.dogdairy.data.entities.dogbreeds.DogBreed


interface IDogBreedRepository {

    suspend fun loadBreeds(): List<DogBreed>
}