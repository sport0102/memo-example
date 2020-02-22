package com.aiden.memo.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _isDataLoading = MutableLiveData(true)
    val isDataLoading: LiveData<Boolean> get() = _isDataLoading

    private val _isDataLoadingError = MutableLiveData(false)
    val isDataLoadingError: LiveData<Boolean> get() = _isDataLoadingError

}