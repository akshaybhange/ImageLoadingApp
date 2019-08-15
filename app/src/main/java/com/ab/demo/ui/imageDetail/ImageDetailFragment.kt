package com.ab.demo.ui.imageDetail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ab.demo.R
import com.ab.demo.base.BaseFragment
import com.ab.demo.databinding.FragmentImageDetailBinding
import javax.inject.Inject

class ImageDetailFragment : BaseFragment<FragmentImageDetailBinding>() {
    override fun layoutRes(): Int {
        return R.layout.fragment_image_detail
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ImageDetailViewModel
    private lateinit var binding: FragmentImageDetailBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ImageDetailViewModel::class.java)
        binding = getDataBinding()
        binding.image = ImageDetailFragmentArgs.fromBundle(arguments!!).image
    }

}
