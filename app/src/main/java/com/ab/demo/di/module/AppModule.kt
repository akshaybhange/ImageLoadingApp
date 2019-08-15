package com.ab.demo.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ab.demo.data.local.ImageDao
import com.ab.demo.data.local.ImageDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    internal fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideDb(app: Application): ImageDb {
        return Room
            .databaseBuilder(app, ImageDb::class.java, "images.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideImageDao(db: ImageDb): ImageDao {
        return db.imageDao()
    }
}