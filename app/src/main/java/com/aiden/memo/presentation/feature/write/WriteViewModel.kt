package com.aiden.memo.presentation.feature.write

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.entity.ThumbnailType
import com.aiden.memo.domain.usecase.GetMemoUseCase
import com.aiden.memo.domain.usecase.InsertMemoUseCase
import com.aiden.memo.domain.usecase.UpdateMemoUseCase
import com.aiden.memo.presentation.base.BaseViewModel
import com.aiden.memo.presentation.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class WriteViewModel(
    private val getMemoUseCase: GetMemoUseCase,
    private val insertMemoUseCase: InsertMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase
) : BaseViewModel() {

    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()

    private val _imageUriList = MutableLiveData<Event<List<String>>>()
    val imageUriList: LiveData<Event<List<String>>> get() = _imageUriList

    private val _imageLinkList = MutableLiveData<Event<List<String>>>()
    val imageLinkList: LiveData<Event<List<String>>> get() = _imageLinkList


    private val _writeType = MutableLiveData<WriteType>()
    val writeType: LiveData<WriteType> get() = _writeType

    private val _status = MutableLiveData<Event<WriteStatusType>>()
    val status: LiveData<Event<WriteStatusType>> get() = _status

    fun setWriteType(writeType: WriteType) = run {
        when (writeType) {
            WriteType.CREATE -> _isDataLoading.value = Event(false)
            WriteType.UPDATE -> {
            }
        }
        _writeType.value = writeType
    }

    fun setImageList(imageList: List<Uri>) = run {
        _imageUriList.value = Event(imageList.map { it.toString() })
    }

    fun checkData(): Boolean {
        if (title.value.isNullOrEmpty()) {
            _status.value = Event(WriteStatusType.NO_TITLE)
            return false
        }
        if (body.value.isNullOrEmpty()) {
            _status.value = Event(WriteStatusType.NO_BODY)
            return false
        }
        return true
    }

    fun saveMemo() {
        val imageUriList = _imageUriList.value?.peekContent()?.map { it.toString() }
        val imageLinkList = _imageLinkList.value?.peekContent()
        val thumbnail = getThumbnail(imageUriList, imageLinkList)
        val memo = Memo(
            title = title.value ?: "",
            body = body.value ?: "",
            thumbnail = thumbnail.first,
            thumbnailType = thumbnail.second,
            imageList = imageUriList,
            imageLink = imageLinkList
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertMemoUseCase(memo)
            } catch (e: IOException) {
                _status.postValue(Event(WriteStatusType.CAN_NOT_SAVE))
            }
            _status.postValue(Event(WriteStatusType.SAVE_DONE))
        }
    }

    private fun getThumbnail(
        imageUriList: List<String>?,
        imageLinkList: List<String>?
    ): Pair<String?, ThumbnailType?> {
        var thumbnail: String? = null
        var thumbnailType: ThumbnailType? = null
        when {
            imageUriList.isNullOrEmpty() && imageLinkList.isNullOrEmpty() -> {
            }
            !imageUriList.isNullOrEmpty() -> {
                thumbnail = imageUriList[0]
                thumbnailType = ThumbnailType.URI
            }
            else -> {
                thumbnail = imageLinkList?.get(0)
                thumbnailType = ThumbnailType.LINK
            }
        }
        return Pair(thumbnail, thumbnailType)
    }
}