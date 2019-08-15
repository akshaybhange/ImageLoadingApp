package com.ab.demo.ui.imageList

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingComponent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.ab.demo.R
import com.ab.demo.base.BaseFragment
import com.ab.demo.databinding.FragmentImageListBinding
import com.ab.demo.ui.common.RetryCallback
import com.ab.demo.util.AppExecutors
import com.ab.demo.util.FragmentDataBindingComponent
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class ImageListFragment : BaseFragment<FragmentImageListBinding>() {

    private lateinit var viewModel: ImageListViewModel
    private lateinit var binding: FragmentImageListBinding

    private lateinit var adapter: ImageAdapter

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var gridSize = 2

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun layoutRes(): Int {
        return R.layout.fragment_image_list
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = getDataBinding()
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ImageListViewModel::class.java)

        binding.query = viewModel.query

        binding.callback = object : RetryCallback {
            override fun retry() {
                viewModel.refresh()
            }

        }

        initSearchInputListener()
        initRecyclerView()
        initGridButtonClick()
    }

    private fun initGridButtonClick() {
        binding.btnGridSize.setOnClickListener {
            val menu = PopupMenu(context!!, it)
            menu.menuInflater.inflate(R.menu.grid_menu, menu.menu)
            menu.setOnMenuItemClickListener { menuItem ->
                when (menuItem!!.itemId) {
                    R.id.menu_2 -> {
                        gridSize = 2
                        binding.rvImageList.layoutManager = GridLayoutManager(context, 2)
                    }
                    R.id.menu_3 -> {
                        gridSize = 3
                        binding.rvImageList.layoutManager = GridLayoutManager(context, 3)
                    }
                    R.id.menu_4 -> {
                        gridSize = 4
                        binding.rvImageList.layoutManager = GridLayoutManager(context, 4)
                    }
                }
                true
            }
            menu.show()

        }
    }

    private fun initSearchInputListener() {
        binding.inputSearch.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.inputSearch.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v: View) {
        val query = binding.inputSearch.text.toString()
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
        viewModel.setQuery(query)
    }


    private fun initRecyclerView() {
        adapter = ImageAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        )
        {
            findNavController().navigate(
                ImageListFragmentDirections.actionImageListFragmentToImageDetailFragment(it)
            )
        }

        binding.rvImageList.layoutManager = GridLayoutManager(context, gridSize)
        binding.rvImageList.adapter = adapter

        binding.rvImageList.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val currentTotalCount = layoutManager.itemCount
                if (currentTotalCount <= lastItem + 2 * gridSize) {
                    viewModel.loadNextPage()
                }
            }
        })

        binding.searchResult = viewModel.results
        viewModel.results.observe(viewLifecycleOwner, Observer { result ->
            result?.data?.let { adapter.submitList(it) }
        })

        viewModel.loadMoreStatus.observe(viewLifecycleOwner, Observer { loadingMore ->
            if (loadingMore == null) {
                binding.loadingMore = false
            } else {
                binding.loadingMore = loadingMore.isRunning
                val error = loadingMore.errorMessageIfNotHandled
                if (error != null) {
                    Snackbar.make(binding.loadMoreBar, error, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}
