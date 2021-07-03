package by.kleban.dogdiary.database

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.database.dao.DogDao
import by.kleban.dogdiary.database.entities.DbDog
import by.kleban.dogdiary.database.entities.DbDogWithPosts
import by.kleban.dogdiary.database.entities.DbPost
import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.DogWithPosts
import by.kleban.dogdiary.entities.Post
import javax.inject.Inject


class DogRoomDb @Inject constructor(
    private val dogDao: DogDao,
    private val dbDogMapper: Mapper<DbDog, Dog>,
    private val dogMapper: Mapper<Dog, DbDog>,
    private val postMapper: Mapper<Post, DbPost>,
    private val dbDogWithPostsMapper: Mapper<DbDogWithPosts, DogWithPosts>
) : DogDb {

    override suspend fun getAllDog(): List<Dog> {
        return dogDao.getAllDog()
            .map { dbDogMapper.map(it) }
    }

    override suspend fun saveDog(dog: Dog): Long {
        val dogDb = dogMapper.map(dog)
        return dogDao.saveDog(dogDb)
    }

    override suspend fun updateDog(dog: Dog, id: Long) {
        val dogDb = dogMapper.map(dog)
        dogDb.id = id
        dogDao.updateDog(dogDb)
    }

    override suspend fun getDogWithPosts(id: Long): DogWithPosts {
        val dbDogWithPosts = dogDao.getDogWithPosts(id)
        return dbDogWithPostsMapper.map(dbDogWithPosts)
    }

    override suspend fun savePost(post: Post) {
        dogDao.savePost(postMapper.map(post))
    }

    override suspend fun updatePost(post: Post) {
        val dbPost = postMapper.map(post)
        dogDao.updatePost(dbPost.description, dbPost.image)
    }

    override suspend fun deletePost(image: String) {
        dogDao.deletePost(image)
    }

    override suspend fun deleteDogWithPosts(id: Long) {
        dogDao.deleteDogWithPosts(id)
    }
}