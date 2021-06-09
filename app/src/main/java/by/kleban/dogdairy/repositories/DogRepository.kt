package by.kleban.dogdairy.repositories

import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogBreed
import by.kleban.dogdairy.entities.DogPost


interface DogRepository {

    suspend fun loadBreeds(): List<DogBreed>

    suspend fun getDog(): Dog?

    suspend fun saveDog(dog: Dog): Long

    suspend fun getDogWithPosts(id: Long): DbDogWithPosts

    suspend fun savePost(post: DogPost)
}