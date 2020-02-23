package com.aiden.memo.data.database.di

import androidx.room.Room
import com.aiden.memo.data.database.MemoDatabase
import com.aiden.memo.data.datasource.memo.MemoDataSource
import com.aiden.memo.data.datasource.memo.MemoLocalDataSource
import com.aiden.memo.data.repository.DefaultMemoRepository
import com.aiden.memo.domain.repository.MemoRepository
import com.aiden.memo.domain.usecase.GetMemoUseCase
import com.aiden.memo.domain.usecase.InsertMemoUseCase
import com.aiden.memo.domain.usecase.UpdateMemoUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun getRepositoryModule() = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            MemoDatabase::class.java, "memo-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<MemoDataSource> {
        MemoLocalDataSource(get())
    }

    single<MemoRepository> {
        DefaultMemoRepository(get())
    }
}
