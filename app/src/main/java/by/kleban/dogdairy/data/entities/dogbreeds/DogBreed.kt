package by.kleban.dogdairy.data.entities.dogbreeds

import com.google.gson.annotations.SerializedName


data class DogBreed(
    val id: Int,
    val image: Image,
    val breed: String,
) {
    data class Image(
        val url: String,
    )
}
