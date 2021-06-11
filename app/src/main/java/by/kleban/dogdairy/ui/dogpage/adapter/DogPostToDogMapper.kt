package by.kleban.dogdairy.ui.dogpage.adapter

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.Post
import javax.inject.Inject


class DogPostToDogMapper @Inject constructor() : Mapper<DogPageAdapter.Item.DogPost, Post> {

    override fun map(from: DogPageAdapter.Item.DogPost): Post {
        return Post(
            dogCreatorId = from.creatorId,
            postImage = from.postImage,
            postDescription = from.postDescription
        )
    }
}