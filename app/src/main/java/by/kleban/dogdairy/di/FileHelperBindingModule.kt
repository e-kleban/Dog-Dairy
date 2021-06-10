package by.kleban.dogdairy.di

import by.kleban.dogdairy.entities.file_helper.FileHelper
import by.kleban.dogdairy.entities.file_helper.FileHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FileHelperBindingModule {

    @Binds
    abstract fun bindFileHelperImpl(fileHelperImpl: FileHelperImpl): FileHelper
}