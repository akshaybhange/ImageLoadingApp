package com.ab.demo.di.module

import com.ab.demo.ui.imageDetail.ImageDetailFragment
import com.ab.demo.ui.imageList.ImageListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeImageListFragment(): ImageListFragment

    @ContributesAndroidInjector
    abstract fun contributeImageDetailFragment(): ImageDetailFragment

}