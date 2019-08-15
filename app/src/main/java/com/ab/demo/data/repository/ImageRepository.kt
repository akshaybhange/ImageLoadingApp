package com.ab.demo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ab.demo.data.Resource
import com.ab.demo.data.local.ImageDao
import com.ab.demo.data.local.ImageDb
import com.ab.demo.data.remote.ApiSuccessResponse
import com.ab.demo.data.remote.api.ImageApi
import com.ab.demo.model.Image
import com.ab.demo.model.ImageSearchResponse
import com.ab.demo.model.ImageSearchResult
import com.ab.demo.util.AbsentLiveData
import com.ab.demo.util.AppExecutors
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: ImageDb,
    private val imageDao: ImageDao,
    private val imageApi: ImageApi
) {
    fun searchNextPage(query: String): LiveData<Resource<Boolean>> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
            query = query,
            imageApi = imageApi,
            db = db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    fun getImages(query: String): LiveData<Resource<List<Image>>> {
        return object : NetworkBoundResource<List<Image>, ImageSearchResponse>(appExecutors) {

            override fun saveCallResult(item: ImageSearchResponse) {
                val repoIds = item.hits.map { it.id }
                val repoSearchResult = ImageSearchResult(
                    query = query,
                    imgIds = repoIds,
                    totalCount = item.total,
                    next = item.nextPage
                )
                db.runInTransaction {
                    imageDao.insertImages(item.hits)
                    imageDao.insert(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<Image>?) = data == null

            override fun loadFromDb(): LiveData<List<Image>> {
                return Transformations.switchMap(imageDao.search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        imageDao.loadOrdered(searchData.imgIds)
                    }
                }
            }

            override fun createCall() = imageApi.getImages(query)

            override fun processResponse(response: ApiSuccessResponse<ImageSearchResponse>)
                    : ImageSearchResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLiveData()
    }
}