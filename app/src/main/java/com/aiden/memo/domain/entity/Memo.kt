package com.aiden.memo.domain.entity

import android.net.Uri
import java.util.*

data class Memo(
    var id: String = UUID.randomUUID().toString(),
    var title: String,
    var body: String,
    var thumbnail: String?,
    var thumbnailType: ThumbnailType?,
    var imageList: List<String>?,
    var imageLinkList: List<String>?
)