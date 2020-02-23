package com.aiden.memo.data.datasource.memo

import androidx.lifecycle.LiveData
import com.aiden.memo.data.database.model.MemoDBModel

interface MemoDataSource {
    fun getAllMemo(): LiveData<List<MemoDBModel>>
    fun getById(id: String): LiveData<MemoDBModel>
    suspend fun insertMemo(memo: MemoDBModel)
    suspend fun insertMemo(memoList: List<MemoDBModel>)
    suspend fun updateMemo(memo: MemoDBModel)
    suspend fun deleteMemo(memo: MemoDBModel)
}