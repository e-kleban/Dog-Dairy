package by.kleban.dogdiary.di

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.entities.DogBreed
import by.kleban.dogdiary.entities.DogFact
import by.kleban.dogdiary.networking.dogbreed.DogApi
import by.kleban.dogdiary.networking.dogbreed.DogRetrofitApi
import by.kleban.dogdiary.networking.dogfacts.DogFactsApi
import by.kleban.dogdiary.networking.dogfacts.DogFactsApiImpl
import by.kleban.dogdiary.networking.entities.response.DogBreedResponse
import by.kleban.dogdiary.networking.entities.response.DogFactResponse
import by.kleban.dogdiary.networking.mappers.DogBreedResponseMapper
import by.kleban.dogdiary.networking.mappers.DogFactResponseMapper
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