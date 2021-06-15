package by.kleban.dogdairy.database

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.dao.DogDao
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.database.entities.DbPost
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogWithPosts
import by.kleban.dogdairy.entities.Post
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
}