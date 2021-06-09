package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.entities.DbPost
import by.kleban.dogdairy.entities.DogPost
import javax.inject.Inject


class PostMapper @Inject constructor() : Mapper<DogPost, DbPost> {

    override fun map(from: DogPost): DbPost {
        return DbPost(
            dogCreatorId = from.dogCreatorId,
            postImage = from.postImage,
            postDescription = from.postDescription
        )
    }
}