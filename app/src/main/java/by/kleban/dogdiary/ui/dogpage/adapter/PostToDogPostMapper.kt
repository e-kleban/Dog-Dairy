package by.kleban.dogdiary.ui.dogpage.adapter

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.entities.Post
import javax.inject.Inject


class PostToDogPostMapper @Inject constructor() : Mapper<Post, DogPageAdapter.Item.DogPost> {

    override fun map(from: Post): DogPageAdapter.Item.DogPost {
        return DogPageAdapter.Item.DogPost(
            description = from.description,
            creatorId = from.dogCreatorId,
            image = from.image,
            thumbnail = from.thumbnail
        )
    }
}