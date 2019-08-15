package com.ab.demo.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ab.demo.R
import com.ab.demo.base.BaseActivity

class MainActivity : BaseActivity() {
    lateinit var navigationController: NavController

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationController = findNavController(R.id.nav_host_fragment)
    }
}
