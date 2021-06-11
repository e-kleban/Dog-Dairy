package by.kleban.dogdairy.di

import by.kleban.dogdairy.core.Mapper
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Post
import by.kleban.dogdairy.ui.dogpage.adapter.DogPageAdapter
import by.kleban.dogdairy.ui.dogpage.adapter.DogPostToDogMapper
import by.kleban.dogdairy.ui.dogpage.adapter.DogToItemHeaderMapper
import by.kleban.dogdairy.ui.dogpage.adapter.PostToDogPostMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class ItemMappersBindingModule {

    @Binds
    abstract fun bindDogPostToDogMapper(dogPostToDogMapper: DogPostToDogMapper): Mapper<DogPageAdapter.Item.DogPost, Post>

    @Binds
    abstract fun bindDogToItemHeaderMapper(dogToItemHeaderMapper: DogToItemHeaderMapper): Mapper<Dog, DogPageAdapter.Item.Header>

    @Binds
    abstract fun bindPostToDogPostMapper(postToDogPostMapper: PostToDogPostMapper): Mapper<Post, DogPageAdapter.Item.DogPost>
}