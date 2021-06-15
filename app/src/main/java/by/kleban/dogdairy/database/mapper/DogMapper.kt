package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.entities.Dog
import javax.inject.Inject


class DogMapper @Inject constructor() : Mapper<Dog, DbDog> {

    override fun map(from: Dog): DbDog {
        return DbDog(
            name = from.name,
            age = from.age,
            breed = from.breed,
            description = from.description,
            sex = from.sex.ordinal,
            image = from.image,
            thumbnail = from.thumbnail
        )
    }
}