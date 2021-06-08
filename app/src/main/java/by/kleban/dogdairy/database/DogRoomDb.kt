package by.kleban.dogdairy.database

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.entities.Dog


class DogRoomDb(
    private val dogDao: DogDao,
    private val dbDogMapper: Mapper<DbDog, Dog>,
    private val dogMapper: Mapper<Dog, DbDog>
) : DogDb {

    override suspend fun getAllDog(): List<Dog> {
        return dogDao.getAllDog()
            .map { dbDogMapper.map(it) }
    }

    override suspend fun saveDog(dog: Dog): Long {
        val dogDb = dogMapper.map(dog)
        return dogDao.saveDog(dogDb)
    }

    override suspend fun getDogWithPosts(id: Long): DbDogWithPosts {
        return dogDao.getDogWithPosts(id)
    }

}