package com.aiden.memo.presentation.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.usecase.DeleteMemoUseCase
import com.aiden.memo.domain.usecase.GetMemoUseCase
import com.aiden.memo.presentation.base.BaseViewModel
import com.aiden.memo.presentation.event.Event
import com.aiden.memo.presentation.model.MemoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class DetailViewModel(
    private val getMemoUseCase: GetMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : BaseViewModel() {
    private lateinit var _memo: LiveData<Event<MemoModel>>
    val memo: LiveData<Event<MemoModel>> get() = _memo

    private var _isDeleted = MutableLiveData<Event<Boolean>>()
    val isDeleted: LiveData<Event<Boolean>> get() = _isDeleted

    fun getMemoById(id: String) {
        _memo = Transformations.switchMap(getMemoUseCase(id)) { memo ->
            MutableLiveData<Event<MemoModel>>(
                Event(
                    MemoModel(
                        id = memo.id,
                        title = memo.title,
                        body = memo.body,
                        thumbnail = memo.thumbnail,
                        thumbnailType = memo.thumbnailType,
                        imageList = memo.imageList,
                        imageLink = memo.imageLink
                    )
                )
            )
        }
    }

    fun deleteMemo() {
        val memoModel = _memo.value?.peekContent()
        val memo = Memo(
            id = memoModel?.id!!,
            title = memoModel.title,
            body = memoModel.body,
            thumbnail = memoModel.thumbnail,
            thumbnailType = memoModel.thumbnailType,
            imageList = memoModel.imageList,
            imageLink = memoModel.imageLink
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteMemoUseCase(memo)
            } catch (e: IOException) {
                _isDeleted.postValue(Event(false))
            }
            _isDeleted.postValue(Event(true))
        }
    }


}