package com.aiden.memo.domain.usecase

import com.aiden.memo.domain.entity.Memo
import com.aiden.memo.domain.repository.MemoRepository


class UpdateMemoUseCase(private val repository: MemoRepository) {
    suspend operator fun invoke(memo: Memo) {
        repository.updateMemo(memo)
    }
}