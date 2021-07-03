package by.kleban.dogdiary.ui.dogpage.adapter

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.entities.Post
import javax.inject.Inject


class DogPostToDogMapper @Inject constructor() : Mapper<DogPageAdapter.Item.DogPost, Post> {

    override fun map(from: DogPageAdapter.Item.DogPost): Post {
        return Post(
            dogCreatorId = from.creatorId,
            image = from.image,
            thumbnail = from.thumbnail,
            description = from.description
        )
    }
}