package com.aiden.memo.domain.usecase

import androidx.lifecycle.LiveData
import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.repository.MemoRepository


class GetMemoUseCase(private val repository: MemoRepository) {
    operator fun invoke(): LiveData<List<Memo>> {
        return repository.getAllMemo()
    }

    operator fun invoke(id: String): LiveData<Memo> {
        return repository.getById(id)
    }
}