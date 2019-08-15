package com.ab.demo.model


import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(
    primaryKeys = ["id"]
)
@SuppressLint("ParcelCreator")
@Parcelize
data class Image(
    val comments: Int,
    val downloads: Int,
    val favorites: Int,
    val fullHDURL: String?,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageURL: String?,
    val imageWidth: Int,
    val largeImageURL: String?,
    val likes: Int,
    val pageURL: String?,
    val previewHeight: Int,
    val previewURL: String?,
    val previewWidth: Int,
    val tags: String?,
    val type: String?,
    val user: String?,
    val userId: Int,
    val userImageURL: String?,
    val views: Int,
    val webformatHeight: Int,
    val webformatURL: String?,
    val webformatWidth: Int
) : Parcelable