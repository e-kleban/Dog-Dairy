package by.kleban.dogdiary.di

import by.kleban.dogdiary.repositories.DogFactsRepository
import by.kleban.dogdiary.repositories.DogFactsRepositoryImpl
import by.kleban.dogdiary.repositories.DogRepository
import by.kleban.dogdiary.repositories.DogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DogRepositoryBindingModule {

    @Binds
    abstract fun bindDogRepositoryImpl(dogRepositoryImpl: DogRepositoryImpl): DogRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class FactsRepositoryBindingModule {

    @Binds
    abstract fun bindDogFactsRepositoryImpl(dogFactsRepositoryImpl: DogFactsRepositoryImpl): DogFactsRepository
}