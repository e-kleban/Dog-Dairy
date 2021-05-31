package by.kleban.dogdairy.database

import by.kleban.dogdairy.entities.Dog


interface DogDb {

    suspend fun getAllDog(): List<Dog>

}