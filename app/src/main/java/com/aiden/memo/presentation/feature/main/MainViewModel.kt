package com.aiden.memo.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aiden.memo.domain.usecase.GetMemoUseCase
import com.aiden.memo.presentation.base.BaseViewModel
import com.aiden.memo.presentation.event.Event

class MainViewModel(getMemoUseCase: GetMemoUseCase) : BaseViewModel() {
    private val _memoList =
        Transformations.switchMap(getMemoUseCase()) {
            val list = mutableListOf<MemoModel>()
            it.forEach { memo ->
                list.add(MemoModel(memo.thumbnail, memo.title, memo.body))
            }
            MutableLiveData<Event<List<MemoModel>>>(Event(list))
        }
    val memoList: LiveData<Event<List<MemoModel>>> get() = _memoList

    private val _hasMemo = MutableLiveData<Event<Boolean>>()
    val hasMemo: LiveData<Event<Boolean>> get() = _hasMemo

    fun setHasMemo(hasMemo: Boolean) = run { _hasMemo.value = Event(hasMemo) }

}