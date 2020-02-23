package com.aiden.memo.presentation.feature.write

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aiden.memo.domain.usecase.GetMemoUseCase
import com.aiden.memo.domain.usecase.InsertMemoUseCase
import com.aiden.memo.domain.usecase.UpdateMemoUseCase
import com.aiden.memo.presentation.base.BaseViewModel
import com.aiden.memo.presentation.event.Event

class WriteViewModel(
    private val getMemoUseCase: GetMemoUseCase,
    private val insertMemoUseCase: InsertMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase
) : BaseViewModel() {
    private val _imageList = MutableLiveData<Event<List<Uri>>>()
    val imageList: LiveData<Event<List<Uri>>> get() = _imageList

    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()

    init {
        _isDataLoading.value = false
    }

    fun setImageList(imageList: List<Uri>) = run { _imageList.value = Event(imageList) }
}