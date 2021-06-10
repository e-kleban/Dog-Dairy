package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.entities.DbPost
import by.kleban.dogdairy.entities.Post
import javax.inject.Inject


class DbPostMapper @Inject constructor() : Mapper<DbPost, Post> {
    override fun map(from: DbPost): Post {
        return Post(
            dogCreatorId = from.dogCreatorId,
            postImage = from.postImage,
            postDescription = from.postDescription
        )
    }
}