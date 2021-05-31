package by.kleban.dogdairy.networking.dogbreed

import by.kleban.dogdairy.networking.entities.response.DogBreedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers


interface DogBreedService {
    @Headers("x-api-key:af2830b3-30aa-4d8b-bc8d-9f3cde8e691c")
    @GET("v1/breeds")
    suspend fun loadBreeds(): Response<List<DogBreedResponse>>
}