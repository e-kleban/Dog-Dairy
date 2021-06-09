package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.database.entities.DbPost
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogWithPosts
import by.kleban.dogdairy.entities.Post
import javax.inject.Inject


class DbDogWithPostsMapper @Inject constructor(
    private val dbDogMapper: Mapper<DbDog, Dog>,
    private val dbPostMapper: Mapper<DbPost, Post>
) : Mapper<DbDogWithPosts, DogWithPosts> {

    override fun map(from: DbDogWithPosts): DogWithPosts {
        return DogWithPosts(
            dog = dbDogMapper.map(from.dbDog),
            posts = from.dbPosts.map { dbPostMapper.map(it) }
        )
    }
}