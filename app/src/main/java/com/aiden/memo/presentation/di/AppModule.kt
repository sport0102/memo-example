package com.aiden.memo.presentation.di

import com.aiden.memo.presentation.feature.detail.DetailViewModel
import com.aiden.memo.presentation.feature.main.MainViewModel
import com.aiden.memo.presentation.feature.write.WriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getAppModule() = module {
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        WriteViewModel(get(), get(), get())
    }
    viewModel {
        DetailViewModel(get(), get())
    }
}