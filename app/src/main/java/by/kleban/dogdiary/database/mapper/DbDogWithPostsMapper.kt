package by.kleban.dogdiary.database.mapper

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.database.entities.DbDog
import by.kleban.dogdiary.database.entities.DbDogWithPosts
import by.kleban.dogdiary.database.entities.DbPost
import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.DogWithPosts
import by.kleban.dogdiary.entities.Post
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