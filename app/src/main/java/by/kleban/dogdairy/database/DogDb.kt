package by.kleban.dogdairy.database

import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.entities.Dog


interface DogDb {

    suspend fun getAllDog(): List<Dog>

    suspend fun saveDog(dog: Dog):Long

    suspend fun getDogWithPosts(id: Long): DbDogWithPosts

}