package com.aiden.memo.data.datasource.memo

import androidx.lifecycle.LiveData
import com.aiden.memo.data.database.MemoDatabase
import com.aiden.memo.data.database.model.MemoDBModel
import java.util.*

class MemoLocalDataSource(private val db: MemoDatabase) : MemoDataSource {
    override fun getAllMemo(): LiveData<List<MemoDBModel>> = db.memoDao().getAll()

    override fun getById(id: String): LiveData<MemoDBModel> = db.memoDao().getById(id)

    override suspend fun insertMemo(memo: MemoDBModel) = db.memoDao().insert(memo)

    override suspend fun insertMemo(memoList: List<MemoDBModel>) = db.memoDao().insert(memoList)

    override suspend fun updateMemo(memo: MemoDBModel) = db.memoDao().update(memo)

}