package com.aiden.memo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aiden.memo.data.database.dao.MemoDao
import com.aiden.memo.data.database.model.MemoDBModel
import com.aiden.memo.data.database.model.converter.MemoTypeConverters

@Database(entities = [MemoDBModel::class], version = 1)
@TypeConverters(MemoTypeConverters::class)
abstract class MemoDatabase : RoomDatabase() {

    abstract fun memoDao(): MemoDao

}