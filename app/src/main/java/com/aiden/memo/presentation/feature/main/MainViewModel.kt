package com.aiden.memo.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aiden.memo.presentation.base.BaseViewModel
import com.aiden.memo.presentation.event.Event

class MainViewModel : BaseViewModel() {
    private val _memoList = MutableLiveData<Event<List<MemoModel>>>()
    val memoList: LiveData<Event<List<MemoModel>>> get() = _memoList

    private val _hasMemo = MutableLiveData<Boolean>()
    val hasMemo: LiveData<Boolean> get() = _hasMemo

    init {
        getMemoList()
    }

    private fun getMemoList() {
        _isDataLoading.value = false
        _hasMemo.value = false
    }
}