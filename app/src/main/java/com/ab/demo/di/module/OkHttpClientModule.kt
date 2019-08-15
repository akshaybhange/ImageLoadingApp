package com.ab.demo.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File


@Suppress("unused")
@Module(includes = [AppModule::class])
public class OkHttpClientModule {

    @Provides
    internal fun provideOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                it.run {
                    var request = it.request()
                    val url = request.url()
                        .newBuilder()
                        .addQueryParameter("key", "13303828-a607e53f8bced6f0197ac82bb")
                        .addQueryParameter("per_page","24")
                        .build()
                    request = request.newBuilder().url(url).build()
                    it.proceed(request)
                }

            }
            .build()
    }

    @Provides
    internal fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, (25 * 1000 * 1000).toLong()) //25 MB //image data
    }

    @Provides
    internal fun provideFile(context: Context): File {
        val file = File(context.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

}