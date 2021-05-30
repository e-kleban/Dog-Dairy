package by.kleban.dogdairy.mappers.dogbreed

import by.kleban.dogdairy.data.dto.dogbreeds.DogBreedResponse
import by.kleban.dogdairy.data.entities.dogbreeds.DogBreed
import by.kleban.dogdairy.mappers.Mapper


class DogBreedMapper : Mapper<DogBreedResponse, DogBreed> {
    override fun map(from: DogBreedResponse): DogBreed {
        return DogBreed(
            id = from.id,
            image = imageMap(from.image),
            breed = from.breed,
            breedGroup = from.bredGroup.orEmpty(),
            height = heightMap(from.height),
            weight = weightMap(from.weight)
        )
    }

    private fun imageMap(from: DogBreedResponse.Image): DogBreed.Image {
        return DogBreed.Image(
            url = from.url
        )
    }

    private fun heightMap(from: DogBreedResponse.Height?): DogBreed.Height {
        return DogBreed.Height(
            centimeter = from?.centimeter.orEmpty()
        )
    }

    private fun weightMap(from: DogBreedResponse.Weight?): DogBreed.Weight {
        return DogBreed.Weight(
            kilogram = from?.kilogram.orEmpty()
        )
    }
}