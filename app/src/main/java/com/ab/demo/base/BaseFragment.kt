package com.ab.demo.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerFragment


abstract class BaseFragment<T : ViewDataBinding> : DaggerFragment() {
    private lateinit var activity: AppCompatActivity
    private lateinit var dataBinding: T


    @LayoutRes
    protected abstract fun layoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        return dataBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity
    }

    fun getBaseActivity(): AppCompatActivity {
        return activity
    }

    protected fun getDataBinding(): T {
        return dataBinding
    }

}