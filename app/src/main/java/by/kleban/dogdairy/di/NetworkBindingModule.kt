package by.kleban.dogdairy.di

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.DogBreed
import by.kleban.dogdairy.networking.dogbreed.DogApi
import by.kleban.dogdairy.networking.dogbreed.DogRetrofitApi
import by.kleban.dogdairy.networking.entities.response.DogBreedResponse
import by.kleban.dogdairy.networking.mappers.DogBreedResponseMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindingModule {

    @Binds
    abstract fun bindDogRetrofitApi(dogRetrofitApi: DogRetrofitApi): DogApi

    @Binds
    abstract fun bindDogBreedResponseMapper(dogBreedResponseMapper: DogBreedResponseMapper): Mapper<DogBreedResponse, DogBreed>
}