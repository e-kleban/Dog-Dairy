package by.kleban.dogdiary.repositories

import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.DogBreed
import by.kleban.dogdiary.entities.DogWithPosts
import by.kleban.dogdiary.entities.Post


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