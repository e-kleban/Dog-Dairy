package by.kleban.dogdairy.adapter.mapper

import by.kleban.dogdairy.adapter.DogPageAdapter
import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.Dog


class DogToItemHeaderMapper: Mapper<Dog, DogPageAdapter.Item.Header> {
    override fun map(from: Dog): DogPageAdapter.Item.Header {
        return DogPageAdapter.Item.Header(
            name = from.name,
            age= from.age,
            breed = from.breed,
            image = from.image,
            description = from.description,
            sex= from.sex
        )
    }
}