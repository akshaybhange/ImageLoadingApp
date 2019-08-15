package com.ab.demo.data.local

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ab.demo.model.Image
import com.ab.demo.model.ImageSearchResult
import java.util.*

@Dao
abstract class ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: ImageSearchResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg images: Image)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImages(images: List<Image>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createImageIfNotExists(repo: Image): Long

    @Query("SELECT * FROM ImageSearchResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<ImageSearchResult>

    fun loadOrdered(repoIds: List<Int>): LiveData<List<Image>> {
        val order = SparseIntArray()
        repoIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return Transformations.map(loadById(repoIds)) { repositories ->
            Collections.sort(repositories) { r1, r2 ->
                val pos1 = order.get(r1.id)
                val pos2 = order.get(r2.id)
                pos1 - pos2
            }
            repositories
        }
    }


    @Query("SELECT * FROM Image WHERE id in (:ids)")
    protected abstract fun loadById(ids: List<Int>): LiveData<List<Image>>

    @Query("SELECT * FROM ImageSearchResult WHERE `query` = :query")
    abstract fun findSearchResult(query: String): ImageSearchResult?
}