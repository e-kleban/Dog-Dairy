package by.kleban.dogdairy.mappers.dogbreed

import by.kleban.dogdairy.data.dto.dogbreeds.DogBreedResponse
import by.kleban.dogdairy.data.entities.dogbreeds.DogBreed
import by.kleban.dogdairy.mappers.Mapper


class DogBreedMapper : Mapper<DogBreedResponse, DogBreed> {
    override fun map(from: DogBreedResponse): DogBreed {
        return DogBreed(
            id = from.id,
            image = imageMap(from.image),
            breed = from.breed
        )
    }

    private fun imageMap(from: DogBreedResponse.Image): DogBreed.Image {
        return DogBreed.Image(
            url = from.url
        )
    }
}