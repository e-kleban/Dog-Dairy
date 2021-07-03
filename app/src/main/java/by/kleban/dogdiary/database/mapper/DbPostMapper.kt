package by.kleban.dogdiary.database.mapper

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.database.entities.DbPost
import by.kleban.dogdiary.entities.Post
import javax.inject.Inject


class DbPostMapper @Inject constructor() : Mapper<DbPost, Post> {
    override fun map(from: DbPost): Post {
        return Post(
            dogCreatorId = from.dogCreatorId,
            description = from.description,
            image = from.image,
            thumbnail = from.thumbnail
        )
    }
}