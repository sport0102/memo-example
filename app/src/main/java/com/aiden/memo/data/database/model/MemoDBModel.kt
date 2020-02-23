package com.aiden.memo.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoDBModel(
    @PrimaryKey
    var id: String,
    var title: String,
    var body: String,
    var thumbnail: String?,
    var thumbnailType: String?,
    var imageList: List<String>?,
    var imageLinkList: List<String>?
)