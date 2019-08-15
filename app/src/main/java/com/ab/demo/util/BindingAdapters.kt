package com.ab.demo.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ab.demo.model.Image
import com.bumptech.glide.Glide

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, image: Image) {
        Glide.with(view)
            .load(image.previewURL)
            .centerCrop()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("loadFullImage")
    fun loadFullImage(view: ImageView, image: Image) {
        Glide.with(view)
            .load(image.previewURL)
            .centerInside()
            .into(view)
    }
}