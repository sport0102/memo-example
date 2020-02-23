package com.aiden.memo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aiden.memo.data.datasource.memo.MemoDataSource
import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.repository.MemoRepository
import java.util.*

class DefaultMemoRepository(
    private val localDataSource: MemoDataSource
) : MemoRepository {

    override fun getAllMemo(): LiveData<List<Memo>> {
        val returnList = mutableListOf<Memo>()
        localDataSource.getAllMemo().value?.forEach {
            returnList.add(
                Memo(
                    id = it.id,
                    title = it.title,
                    body = it.body,
                    thumbnail = it.thumbnail,
                    imageList = it.imageList,
                    imageLink = it.imageLink
                )
            )
        }
        return MutableLiveData<List<Memo>>(returnList)
    }


    override fun getById(id: UUID): LiveData<Memo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertMemo(memo: Memo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertMemo(memoList: List<Memo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateMemo(memo: Memo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
