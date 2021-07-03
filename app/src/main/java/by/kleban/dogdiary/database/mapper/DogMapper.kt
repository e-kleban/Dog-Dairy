package by.kleban.dogdiary.database.mapper

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.database.entities.DbDog
import by.kleban.dogdiary.entities.Dog
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