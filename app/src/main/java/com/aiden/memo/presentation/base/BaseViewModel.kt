package com.aiden.memo.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aiden.memo.presentation.event.Event

abstract class BaseViewModel : ViewModel() {

    protected val _isDataLoading = MutableLiveData(Event(true))
    val isDataLoading: LiveData<Event<Boolean>> get() = _isDataLoading

    protected val _isDataLoadingError = MutableLiveData(Event("" to false))
    val isDataLoadingError: LiveData<Event<Pair<String, Boolean>>> get() = _isDataLoadingError

    fun setDataLoadingStatus(status: Boolean) = run { _isDataLoading.value = Event(status) }
    fun setDataLoadingErrorStatus(reason: String, status: Boolean) =
        run { _isDataLoadingError.value = Event(reason to status) }

}