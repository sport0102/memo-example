package com.aiden.memo.domain.di

import com.aiden.memo.domain.usecase.GetMemoUseCase
import com.aiden.memo.domain.usecase.InsertMemoUseCase
import com.aiden.memo.domain.usecase.UpdateMemoUseCase
import org.koin.dsl.module

fun getUseCaseModule() = module {
    single {
        GetMemoUseCase(get())
    }

    single {
        InsertMemoUseCase(get())
    }

    single {
        UpdateMemoUseCase(get())
    }
}
