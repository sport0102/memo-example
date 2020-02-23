package com.aiden.memo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aiden.memo.data.database.model.MemoDBModel

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo")
    fun getAll(): LiveData<List<MemoDBModel>>

    @Query("SELECT * FROM memo where id =:id")
    fun getById(id: String): LiveData<MemoDBModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memoList: List<MemoDBModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: MemoDBModel)

    @Update
    fun update(memo: MemoDBModel)

    @Delete
    fun delete(memo: MemoDBModel)
}