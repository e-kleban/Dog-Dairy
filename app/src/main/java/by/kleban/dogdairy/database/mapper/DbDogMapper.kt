package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.entities.Dog


class DbDogMapper : Mapper<DbDog, Dog> {

    override fun map(from: DbDog): Dog {
        return Dog(
            name = from.name,
            image = from.image,
            age = from.age,
            sex = from.sex,
            breed = from.breed,
            description = from.description,
        )
    }
}