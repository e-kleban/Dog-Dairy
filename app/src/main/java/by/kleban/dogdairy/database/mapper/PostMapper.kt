package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.entities.DbPost
import by.kleban.dogdairy.entities.Post
import javax.inject.Inject


class PostMapper @Inject constructor() : Mapper<Post, DbPost> {

    override fun map(from: Post): DbPost {
        return DbPost(
            dogCreatorId = from.dogCreatorId,
            postDescription = from.postDescription,
            postBigImage = from.postBigImage,
            postLittleImage = from.postLittleImage
        )
    }
}