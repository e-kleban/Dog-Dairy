package by.kleban.dogdairy.data.dto.dogbreeds


import com.google.gson.annotations.SerializedName

data class DogBreedResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Image,
    @SerializedName("name")
    val breed: String,
    @SerializedName("breed_group")
    val bredGroup: String?,
    @SerializedName("height")
    val height: Height?,
    @SerializedName("weight")
    val weight: Weight?
) {
    data class Image(
        @SerializedName("url")
        val url: String,
    )

    data class Height(
        @SerializedName("metric")
        val centimeter: String?
    )

    data class Weight(
        @SerializedName("metric")
        val kilogram: String?
    )
}