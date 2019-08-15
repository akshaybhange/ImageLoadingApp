package com.ab.demo.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import dagger.android.support.DaggerAppCompatActivity


public abstract class BaseActivity : DaggerAppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
    }
}