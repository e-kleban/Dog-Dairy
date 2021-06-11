package by.kleban.dogdairy.ui.dogpage.adapter

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.Post
import javax.inject.Inject


class PostToDogPostMapper @Inject constructor() : Mapper<Post, DogPageAdapter.Item.DogPost> {

    override fun map(from: Post): DogPageAdapter.Item.DogPost {
        return DogPageAdapter.Item.DogPost(
            postDescription = from.description,
            creatorId = from.dogCreatorId,
            postBigImage = from.image,
            postLittleImage = from.thumbnail
        )
    }
}