package by.kleban.dogdairy.data.dto.dogbreeds


import com.google.gson.annotations.SerializedName

data class DogBreedResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("name")
    val breed: String?,
) {
    data class Image(
        @SerializedName("url")
        val url: String?,
    )
}