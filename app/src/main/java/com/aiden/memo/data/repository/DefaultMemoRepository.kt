package com.aiden.memo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aiden.memo.data.database.model.MemoDBModel
import com.aiden.memo.data.datasource.memo.MemoDataSource
import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.entity.ThumbnailType
import com.aiden.memo.domain.repository.MemoRepository

class DefaultMemoRepository(
    private val localDataSource: MemoDataSource
) : MemoRepository {

    override fun getAllMemo(): LiveData<List<Memo>> {
        return Transformations.switchMap(localDataSource.getAllMemo()) {
            val list = mutableListOf<Memo>()
            it.forEach { memo ->
                list.add(dbMemoToViewMemo(memo))
            }
            MutableLiveData<List<Memo>>(list)
        }
    }

    private fun getThumbnailType(thumbnailType: String?): ThumbnailType? {
        val type: ThumbnailType? = null
        when (thumbnailType) {
            ThumbnailType.URI.name -> ThumbnailType.URI
            ThumbnailType.LINK.name -> ThumbnailType.LINK
        }
        return type
    }


    override fun getById(id: String): LiveData<Memo> {
        return Transformations.switchMap(localDataSource.getById(id)) {
            it?.let {
                MutableLiveData<Memo>(dbMemoToViewMemo(it))
            }
        }
    }

    override suspend fun insertMemo(memo: Memo) {
        localDataSource.insertMemo(entityMemoToDBMemo(memo))
    }

    override suspend fun insertMemo(memoList: List<Memo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateMemo(memo: Memo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteMemo(memo: Memo) {
        localDataSource.deleteMemo(entityMemoToDBMemo(memo))
    }

    private fun entityMemoToDBMemo(memo: Memo): MemoDBModel {
        return MemoDBModel(
            id = memo.id,
            title = memo.title,
            body = memo.body,
            thumbnail = memo.thumbnail,
            thumbnailType = memo.thumbnailType?.name,
            imageList = memo.imageList,
            imageLink = memo.imageLink
        )
    }

    private fun dbMemoToViewMemo(memo: MemoDBModel): Memo {
        return Memo(
            id = memo.id,
            title = memo.title,
            body = memo.body,
            thumbnail = memo.thumbnail,
            thumbnailType = getThumbnailType(memo.thumbnailType),
            imageList = memo.imageList,
            imageLink = memo.imageLink
        )
    }

}
