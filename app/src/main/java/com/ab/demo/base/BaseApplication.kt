package com.ab.demo.base

import com.ab.demo.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class BaseApplication : DaggerApplication() {

    companion object {
        @get:Synchronized
        @set:Synchronized
        var instance: BaseApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder().application(this).build()
        component.inject(this)

        return component
    }

}