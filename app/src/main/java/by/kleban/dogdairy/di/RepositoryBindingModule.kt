package by.kleban.dogdairy.di

import by.kleban.dogdairy.repositories.DogRepository
import by.kleban.dogdairy.repositories.DogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryBindingModule {

    @Binds
    abstract fun bindDogRepositoryImpl(dogRepositoryImpl: DogRepositoryImpl): DogRepository
}