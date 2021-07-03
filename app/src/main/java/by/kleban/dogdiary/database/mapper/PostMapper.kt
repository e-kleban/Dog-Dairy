package by.kleban.dogdiary.database.mapper

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.database.entities.DbPost
import by.kleban.dogdiary.entities.Post
import javax.inject.Inject


class PostMapper @Inject constructor() : Mapper<Post, DbPost> {

    override fun map(from: Post): DbPost {
        return DbPost(
            dogCreatorId = from.dogCreatorId,
            description = from.description,
            image = from.image,
            thumbnail = from.thumbnail
        )
    }
}