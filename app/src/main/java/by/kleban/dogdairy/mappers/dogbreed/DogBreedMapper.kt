package by.kleban.dogdairy.mappers.dogbreed

import by.kleban.dogdairy.data.dto.dogbreeds.DogBreedResponse
import by.kleban.dogdairy.data.entities.dogbreeds.DogBreed
import com.bignerdranch.android.a1305network.mappers.Mapper


class DogBreedMapper : Mapper<DogBreedResponse, DogBreed> {
    override fun map(from: DogBreedResponse): DogBreed {
        return DogBreed(
            id = from.id ?: -1,
            image = imageMap(from.image),
            breed = from.breed.orEmpty()
        )
    }

    private fun imageMap(from: DogBreedResponse.Image?): DogBreed.Image {
        return DogBreed.Image(
            url = from?.url.orEmpty()
        )
    }
}