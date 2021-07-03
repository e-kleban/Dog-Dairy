package by.kleban.dogdiary.networking.dogfacts

import by.kleban.dogdiary.networking.entities.response.DogFactResponse
import retrofit2.Response
import retrofit2.http.GET


interface DogFactService {
    @GET("api/v1/resources/dogs/all")
    suspend fun loadFacts(): Response<List<DogFactResponse>>
}