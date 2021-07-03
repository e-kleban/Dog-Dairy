package by.kleban.dogdiary.networking.mappers

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.entities.DogFact
import by.kleban.dogdiary.networking.entities.response.DogFactResponse
import javax.inject.Inject


class DogFactResponseMapper @Inject constructor() : Mapper<DogFactResponse, DogFact> {
    override fun map(from: DogFactResponse): DogFact {
        return DogFact(from.fact)
    }
}