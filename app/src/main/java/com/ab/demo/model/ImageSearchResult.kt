package com.ab.demo.model

import androidx.room.Entity
import androidx.room.TypeConverters
import com.ab.demo.data.local.DemoTypeConverters

@Entity(primaryKeys = ["query"])
@TypeConverters(DemoTypeConverters::class)
data class ImageSearchResult(
    val query: String,
    val imgIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)
