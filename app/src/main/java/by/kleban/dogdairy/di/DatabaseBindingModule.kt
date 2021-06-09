package by.kleban.dogdairy.di

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.DogDb
import by.kleban.dogdairy.database.DogRoomDb
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.mapper.DbDogMapper
import by.kleban.dogdairy.database.mapper.DogMapper
import by.kleban.dogdairy.entities.Dog
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseBindingModule {

    @Binds
    abstract fun bindDbDogMapper(dbDogMapper: DbDogMapper): Mapper<DbDog, Dog>

    @Binds
    abstract fun bindDogMapper(dogMapper: DogMapper): Mapper<Dog, DbDog>

    @Binds
    abstract fun bindDogRoomDb(dogRoomDb: DogRoomDb): DogDb

}