package com.ab.demo.di.module

import com.ab.demo.data.remote.api.ImageApi
import com.ab.demo.util.LiveDataCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Suppress("unused")
@Module(includes = [OkHttpClientModule::class])
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://pixabay.com/"
    }

    @Provides
    fun provideImageApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): ImageApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ImageApi::class.java)

    }

    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }


    @Provides
    internal fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


}