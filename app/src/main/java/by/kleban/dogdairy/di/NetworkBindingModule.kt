package by.kleban.dogdairy.di

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.DogBreed
import by.kleban.dogdairy.entities.DogFact
import by.kleban.dogdairy.networking.dogbreed.DogApi
import by.kleban.dogdairy.networking.dogbreed.DogRetrofitApi
import by.kleban.dogdairy.networking.dogfacts.DogFactsApi
import by.kleban.dogdairy.networking.dogfacts.DogFactsApiImpl
import by.kleban.dogdairy.networking.entities.response.DogBreedResponse
import by.kleban.dogdairy.networking.entities.response.DogFactResponse
import by.kleban.dogdairy.networking.mappers.DogBreedResponseMapper
import by.kleban.dogdairy.networking.mappers.DogFactResponseMapper
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

    @Binds
    abstract fun bindDogFactApiImpl(dogFactsApiImpl: DogFactsApiImpl): DogFactsApi

    @Binds
    abstract fun bindDogFactResponseMapper(dogFactResponseMapper: DogFactResponseMapper): Mapper<DogFactResponse, DogFact>
}