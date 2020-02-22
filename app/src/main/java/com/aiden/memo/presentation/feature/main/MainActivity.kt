package com.aiden.memo.presentation.feature.main

import androidx.lifecycle.ViewModelProvider
import com.aiden.memo.R
import com.aiden.memo.databinding.ActivityMainBinding
import com.aiden.memo.presentation.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
}