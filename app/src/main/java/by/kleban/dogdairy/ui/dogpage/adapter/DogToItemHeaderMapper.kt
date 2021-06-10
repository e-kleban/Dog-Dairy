package by.kleban.dogdairy.ui.dogpage.adapter

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.ui.dogpage.adapter.DogPageAdapter


class DogToItemHeaderMapper : Mapper<Dog, DogPageAdapter.Item.Header> {
    override fun map(from: Dog): DogPageAdapter.Item.Header {
        return DogPageAdapter.Item.Header(
            name = from.name,
            age = from.age,
            breed = from.breed,
            image = from.image,
            description = from.description,
            sex = from.sex
        )
    }
}