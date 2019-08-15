package com.ab.demo.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ab.demo.di.annotation.ViewModelKey
import com.ab.demo.factory.ViewModelFactory
import com.ab.demo.ui.imageDetail.ImageDetailViewModel
import com.ab.demo.ui.imageList.ImageListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(ImageListViewModel::class)
    abstract fun bindImageListViewModel(imageListViewModel: ImageListViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(ImageDetailViewModel::class)
    abstract fun bindImageDetailViewModel(imageDetailViewModel: ImageDetailViewModel): ViewModel
}