package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.DogDb
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.entities.Dog


class DogMapper : Mapper<Dog, DbDog> {

    override fun map(from: Dog): DbDog {
        return DbDog(
            name = from.name,
            image = from.image,
            age = from.age,
            sex = from.sex,
            breed = from.breed,
            description = from.description,
        )
    }
}