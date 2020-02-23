package com.aiden.memo.domain.usecase

import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.repository.MemoRepository


class InsertMemoUseCase(private val repository: MemoRepository) {
    suspend fun invoke(memoList: List<Memo>) {
        repository.insertMemo(memoList)
    }

    suspend fun invoke(memo: Memo) {
        repository.insertMemo(memo)
    }
}