package by.kleban.dogdairy.entities


data class Dog(
    val name: String,
    val bigImage: String,
    val littleImage: String,
    val age: Int,
    val sex: Sex,
    val breed: String,
    val description: String
)
