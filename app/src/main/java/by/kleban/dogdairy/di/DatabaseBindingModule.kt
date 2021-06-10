package by.kleban.dogdairy.di

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.database.DogDb
import by.kleban.dogdairy.database.DogRoomDb
import by.kleban.dogdairy.database.entities.DbDog
import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.database.entities.DbPost
import by.kleban.dogdairy.database.mapper.*
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.DogWithPosts
import by.kleban.dogdairy.entities.Post
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
    abstract fun bindPostMapper(postMapper: PostMapper): Mapper<Post, DbPost>

    @Binds
    abstract fun bindDbPostMapper(dbPostMapper: DbPostMapper): Mapper<DbPost, Post>

    @Binds
    abstract fun bindDbDogWithPostsMapper(dbDogWithPostsMapper: DbDogWithPostsMapper): Mapper<DbDogWithPosts, DogWithPosts>

    @Binds
    abstract fun bindDogRoomDb(dogRoomDb: DogRoomDb): DogDb

}