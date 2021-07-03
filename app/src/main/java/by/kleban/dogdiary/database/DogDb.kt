package by.kleban.dogdiary.database

import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.DogWithPosts
import by.kleban.dogdiary.entities.Post


interface DogDb {

    suspend fun getAllDog(): List<Dog>

    suspend fun saveDog(dog: Dog): Long

    suspend fun updateDog(dog: Dog, id: Long)

    suspend fun getDogWithPosts(id: Long): DogWithPosts

    suspend fun savePost(post: Post)

    suspend fun updatePost(post: Post)

    suspend fun deletePost(image: String)

    suspend fun deleteDogWithPosts(id: Long)
}