package com.ab.demo.model


import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ImageSearchResponse(
    val hits: List<Image>,
    val total: Int,
    val totalHits: Int
) : Parcelable{
    var nextPage: Int? = null
}