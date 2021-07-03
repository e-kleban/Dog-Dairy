package by.kleban.dogdiary.networking.entities.response


import com.google.gson.annotations.SerializedName

data class DogFactResponse(
    @SerializedName("fact")
    val fact: String
)
