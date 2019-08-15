package com.ab.demo.di.component

import android.app.Application
import com.ab.demo.base.BaseApplication
import com.ab.demo.di.module.ActivityBuildersModule
import com.ab.demo.di.module.NetworkModule
import com.ab.demo.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        ActivityBuildersModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: BaseApplication)
}