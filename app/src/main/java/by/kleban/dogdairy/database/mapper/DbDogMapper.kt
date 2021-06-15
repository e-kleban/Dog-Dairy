package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Sex
import javax.inject.Inject


class DbDogMapper @Inject constructor() : Mapper<DbDog, Dog> {

    override fun map(from: DbDog): Dog {
        return Dog(
            name = from.name,
            age = from.age,
            breed = from.breed,
            description = from.description,
            sex = Sex.values()[from.sex],
            image = from.image,
            thumbnail = from.thumbnail
        )
    }
}