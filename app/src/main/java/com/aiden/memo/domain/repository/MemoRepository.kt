package com.aiden.memo.domain.repository

import androidx.lifecycle.LiveData
import com.aiden.memo.data.database.model.MemoDBModel
import com.aiden.memo.domain.entity.Memo
import java.util.*

interface MemoRepository {
    fun getAllMemo(): LiveData<List<Memo>>
    fun getById(id: UUID): LiveData<Memo>
    suspend fun insertMemo(memo: Memo)
    suspend fun insertMemo(memoList: List<Memo>)
    suspend fun updateMemo(memo: Memo)
    suspend fun deleteMemo(memo: Memo)
}