package com.ab.demo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ab.demo.data.Resource
import com.ab.demo.data.local.ImageDb
import com.ab.demo.data.remote.ApiEmptyResponse
import com.ab.demo.data.remote.ApiErrorResponse
import com.ab.demo.data.remote.ApiResponse
import com.ab.demo.data.remote.ApiSuccessResponse
import com.ab.demo.data.remote.api.ImageApi
import com.ab.demo.model.ImageSearchResult
import java.io.IOException

class FetchNextSearchPageTask constructor(
    private val query: String,
    private val imageApi: ImageApi,
    private val db: ImageDb
) : Runnable {
    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.imageDao().findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        if (nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }
        val newValue = try {
            val response = imageApi.getImages(query, nextPage).execute()
            val apiResponse = ApiResponse.create(response)
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    // we merge all repo ids into 1 list so that it is easier to fetch the
                    // result list.
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.imgIds)

                    ids.addAll(apiResponse.body.hits.map { it.id })
                    val merged = ImageSearchResult(
                        query, ids,
                        apiResponse.body.total, apiResponse.nextPage
                    )
                    db.runInTransaction {
                        db.imageDao().insert(merged)
                        db.imageDao().insertImages(apiResponse.body.hits)
                    }
                    Resource.success(apiResponse.nextPage != null)
                }
                is ApiEmptyResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }
            }

        } catch (e: IOException) {
            Resource.error(e.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}
