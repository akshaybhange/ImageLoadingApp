package com.ab.demo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ab.demo.model.Image
import com.ab.demo.model.ImageSearchResult

@Database(
    entities = [
        Image::class,
        ImageSearchResult::class
    ],
    version = 1
)
abstract class ImageDb : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}