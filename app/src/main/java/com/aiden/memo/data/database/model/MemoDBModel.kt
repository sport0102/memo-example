package com.aiden.memo.data.database.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoDBModel(
    @PrimaryKey
    var id: String,
    var title: String,
    var body: String,
    var thumbnail: String?,
    var imageList: List<Uri>?,
    var imageLink: String?
)