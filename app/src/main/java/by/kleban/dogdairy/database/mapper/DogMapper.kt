package by.kleban.dogdairy.database.mapper

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Sex


class DogMapper : Mapper<Dog, DbDog> {

    override fun map(from: Dog): DbDog {
        return DbDog(
            name = from.name,
            image = from.image,
            age = from.age,
            breed = from.breed,
            description = from.description,
            sex = if (from.sex == Sex.FEMALE) {
                Sex.FEMALE.ordinal
            } else {
                Sex.MALE.ordinal
            }
        )
    }
}