package com.ab.demo.ui.imageList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ab.demo.R
import com.ab.demo.databinding.ItemImageListBinding
import com.ab.demo.model.Image
import com.ab.demo.ui.common.DataBoundListAdapter
import com.ab.demo.util.AppExecutors

class ImageAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val imageClickCallback: ((Image) -> Unit)?
) : DataBoundListAdapter<Image, ItemImageListBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.previewURL == newItem.previewURL
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ItemImageListBinding {
        val binding = DataBindingUtil.inflate<ItemImageListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_image_list,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.image?.let {
                imageClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemImageListBinding, item: Image) {
        binding.image = item
    }
}