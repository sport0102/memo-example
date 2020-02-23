package com.aiden.memo.domain.usecase

import androidx.lifecycle.LiveData
import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.repository.MemoRepository
import java.util.*


class GetMemoUseCase(private val repository: MemoRepository) {
    operator fun invoke(): LiveData<List<Memo>> {
        return repository.getAllMemo()
    }

    operator fun invoke(id: UUID): LiveData<Memo> {
        return repository.getById(id)
    }
}