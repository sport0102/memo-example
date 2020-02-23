package com.aiden.memo.presentation.feature.write

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.entity.ThumbnailType
import com.aiden.memo.domain.usecase.GetMemoUseCase
import com.aiden.memo.domain.usecase.InsertMemoUseCase
import com.aiden.memo.domain.usecase.UpdateMemoUseCase
import com.aiden.memo.presentation.base.BaseViewModel
import com.aiden.memo.presentation.event.Event
import com.aiden.memo.presentation.model.MemoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class WriteViewModel(
    private val getMemoUseCase: GetMemoUseCase,
    private val insertMemoUseCase: InsertMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase
) : BaseViewModel() {

    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()

    private val _imageUriList = MutableLiveData<List<String>>()
    val imageUriList: LiveData<List<String>> get() = _imageUriList

    private val _imageLinkList = MutableLiveData<List<String>>()
    val imageLinkList: LiveData<List<String>> get() = _imageLinkList

    private val _writeType = MutableLiveData<WriteType>()
    val writeType: LiveData<WriteType> get() = _writeType

    private val _status = MutableLiveData<Event<WriteStatusType>>()
    val status: LiveData<Event<WriteStatusType>> get() = _status

    private var _memo: LiveData<Event<MemoModel>> = MutableLiveData()
    val memo: LiveData<Event<MemoModel>> get() = _memo

    fun setWriteType(
        writeType: WriteType,
        memoId: String?
    ) = run {
        when (writeType) {
            WriteType.CREATE -> _isDataLoading.value = Event(false)
            WriteType.UPDATE -> {
                memoId?.let {
                    _memo = Transformations.switchMap(getMemoUseCase(memoId)) { memo ->
                        MutableLiveData<Event<MemoModel>>(
                            Event(
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
                        )
                    }
                }
            }
        }
        _writeType.value = writeType
    }

    fun setMemoData() {
        val memo = _memo.value?.peekContent()
        memo?.let {
            title.value = it.title
            body.value = it.body
            _imageUriList.value = it.imageList ?: mutableListOf()
            _imageLinkList.value = it.imageLinkList ?: mutableListOf()
        }
    }

    fun setImageList(imageList: List<Uri>) = run {
        _imageUriList.value = imageList.map { it.toString() }
    }

    fun addImageLink(link: String) = run {
        _imageLinkList.value =
            _imageLinkList.value?.toMutableList()?.apply { add(link) } ?: mutableListOf(link)
    }

    fun deleteSelectedImage(position: Int) {
        val imageUriList =
            _imageUriList.value?.toMutableList()?.apply { removeAt(position) }
        imageUriList?.let {
            _imageUriList.value = it
        }
    }

    fun deleteLink(position: Int) {
        val linkList =
            _imageLinkList.value?.toMutableList()?.apply { removeAt(position) }
        linkList?.let {
            _imageLinkList.value = it
        }
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
        val imageUriList = _imageUriList.value
        val imageLinkList = _imageLinkList.value
        val thumbnail = getThumbnail(imageUriList, imageLinkList)
        val memo = Memo(
            id = _memo.value?.peekContent()?.id ?: UUID.randomUUID().toString(),
            title = title.value ?: "",
            body = body.value ?: "",
            thumbnail = thumbnail.first,
            thumbnailType = thumbnail.second,
            imageList = imageUriList,
            imageLinkList = imageLinkList
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (writeType.value) {
                    WriteType.CREATE -> insertMemoUseCase(memo)
                    WriteType.UPDATE -> updateMemoUseCase(memo)
                }
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