package by.kleban.dogdairy.entities


data class DogBreed(
    val id: Int,
    val image: Image,
    val breed: String,
    val breedGroup: String,
    val height: Height,
    val weight: Weight
) {
    data class Image(
        val url: String,
    )

    data class Height(
        val centimeter: String
    )

    data class Weight(
        val kilogram: String
    )
}
