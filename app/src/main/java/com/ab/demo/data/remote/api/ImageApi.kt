package com.ab.demo.data.remote.api

import androidx.lifecycle.LiveData
import com.ab.demo.data.remote.ApiResponse
import com.ab.demo.model.ImageSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("api/")
    fun getImages(@Query("q") query: String): LiveData<ApiResponse<ImageSearchResponse>>

    @GET("api/")
    fun getImages(@Query("q") query: String, @Query("page") page: Int): Call<ImageSearchResponse>
}