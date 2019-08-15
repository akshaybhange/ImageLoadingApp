package com.ab.demo.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class QueryParamsInterceptor : Interceptor {
    companion object {

        private const val KEY_API_KEY = "key"
        private const val VAL_API_KEY = "13303828-a607e53f8bced6f0197ac82bb"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val urlBuilder = original.url().newBuilder()

        urlBuilder.addQueryParameter(KEY_API_KEY, VAL_API_KEY)

        val request = original.newBuilder().build()
        return chain.proceed(request)
    }

}