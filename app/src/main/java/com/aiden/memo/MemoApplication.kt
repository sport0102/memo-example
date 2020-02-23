package com.aiden.memo

import android.app.Application
import com.aiden.memo.data.database.di.getRepositoryModule
import com.aiden.memo.domain.di.getUseCaseModule
import com.aiden.memo.presentation.di.getAppModule

class MemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin(
            this,
            getAppModule(),
            getRepositoryModule(),
            getUseCaseModule()
        )
    }
}