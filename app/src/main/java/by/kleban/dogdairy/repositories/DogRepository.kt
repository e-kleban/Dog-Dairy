package by.kleban.dogdairy.repositories

import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogBreed
import by.kleban.dogdairy.entities.DogWithPosts
import by.kleban.dogdairy.entities.Post


interface DogRepository {

    suspend fun loadBreeds(): List<DogBreed>

    suspend fun getDog(): Dog?

    suspend fun saveDog(dog: Dog)

    suspend fun updateDog(dog: Dog)

    suspend fun getDogWithPosts(): DogWithPosts

    suspend fun savePost(post: Post)

    suspend fun updatePost(post: Post)

    suspend fun deletePost(image: String)

    suspend fun deleteDogWithPosts()
}