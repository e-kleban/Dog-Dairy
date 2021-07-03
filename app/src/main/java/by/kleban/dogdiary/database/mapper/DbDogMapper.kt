package by.kleban.dogdiary.database.mapper

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.database.entities.DbDog
import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.Sex
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