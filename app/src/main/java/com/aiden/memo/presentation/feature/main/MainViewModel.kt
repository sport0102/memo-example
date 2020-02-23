package com.aiden.memo.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aiden.memo.domain.usecase.GetMemoUseCase
import com.aiden.memo.presentation.base.BaseViewModel
import com.aiden.memo.presentation.event.Event
import com.aiden.memo.presentation.model.MemoModel

class MainViewModel(getMemoUseCase: GetMemoUseCase) : BaseViewModel() {
    private val _memoList =
        Transformations.switchMap(getMemoUseCase()) {
            val list = mutableListOf<MemoModel>()
            it.forEach { memo ->
                list.add(
                    MemoModel(
                        id = memo.id,
                        title = memo.title,
                        body = memo.body,
                        thumbnail = memo.thumbnail,
                        thumbnailType = memo.thumbnailType,
                        imageList = memo.imageList,
                        imageLinkList = memo.imageLinkList
                    )
                )
            }
            MutableLiveData<List<MemoModel>>(list)
        }
    val memoList: LiveData<List<MemoModel>> get() = _memoList

    private val _hasMemo = MutableLiveData<Event<Boolean>>()
    val hasMemo: LiveData<Event<Boolean>> get() = _hasMemo

    private val _selectedItem = MutableLiveData<Event<String>>()
    val selectedItem: LiveData<Event<String>> get() = _selectedItem

    fun setHasMemo(hasMemo: Boolean) = run { _hasMemo.value = Event(hasMemo) }

    fun setSelectedItem(id: String) = run { _selectedItem.value = Event(id) }

}