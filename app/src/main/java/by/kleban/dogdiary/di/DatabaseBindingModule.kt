package by.kleban.dogdiary.di

import by.kleban.dogdiary.core.Mapper
import by.kleban.dogdiary.database.DogDb
import by.kleban.dogdiary.database.DogRoomDb
import by.kleban.dogdiary.database.entities.DbDog
import by.kleban.dogdiary.database.entities.DbDogWithPosts
import by.kleban.dogdiary.database.entities.DbPost
import by.kleban.dogdiary.database.mapper.*
import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.DogWithPosts
import by.kleban.dogdiary.entities.Post
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