package by.kleban.dogdairy.ui.dogpage.adapter

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.Dog
import javax.inject.Inject


class DogToItemHeaderMapper @Inject constructor() : Mapper<Dog, DogPageAdapter.Item.Header> {

    override fun map(from: Dog): DogPageAdapter.Item.Header {
        return DogPageAdapter.Item.Header(
            name = from.name,
            age = from.age,
            breed = from.breed,
            description = from.description,
            sex = from.sex,
            image = from.image,
            thumbnail = from.thumbnail
        )
    }
}