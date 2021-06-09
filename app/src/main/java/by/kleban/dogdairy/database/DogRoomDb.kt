package by.kleban.dogdairy.database

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.database.entities.DbPost
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogPost
import javax.inject.Inject


class DogRoomDb @Inject constructor(
    private val dogDao: DogDao,
    private val dbDogMapper: Mapper<DbDog, Dog>,
    private val dogMapper: Mapper<Dog, DbDog>,
    private val postMapper: Mapper<DogPost, DbPost>
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

    override suspend fun savePost(post: DogPost) {
        dogDao.savePost(postMapper.map(post))
    }

}