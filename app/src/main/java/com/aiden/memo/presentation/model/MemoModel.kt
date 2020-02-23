package com.aiden.memo.presentation.model

import com.aiden.memo.domain.entity.ThumbnailType

data class MemoModel(
    var id: String,
    var title: String,
    var body: String,
    var thumbnail: String?,
    var thumbnailType: ThumbnailType?,
    var imageList: List<String>?,
    var imageLink: List<String>?
)