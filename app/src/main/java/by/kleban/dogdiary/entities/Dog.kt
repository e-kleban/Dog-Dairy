package by.kleban.dogdiary.entities


data class Dog(
    val name: String,
    val image: String,
    val thumbnail: String,
    val age: Int,
    val sex: Sex,
    val breed: String,
    val description: String
)
