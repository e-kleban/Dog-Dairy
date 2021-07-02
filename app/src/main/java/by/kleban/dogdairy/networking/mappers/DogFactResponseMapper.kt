package by.kleban.dogdairy.networking.mappers

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.DogFact
import by.kleban.dogdairy.networking.entities.response.DogFactResponse
import javax.inject.Inject


class DogFactResponseMapper @Inject constructor() : Mapper<DogFactResponse, DogFact> {
    override fun map(from: DogFactResponse): DogFact {
        return DogFact(from.fact)
    }
}