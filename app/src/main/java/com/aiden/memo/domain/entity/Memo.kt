package com.aiden.memo.domain.entity

import android.net.Uri
import java.util.*

data class Memo(
    var id: String = UUID.randomUUID().toString(),
    var title: String,
    var body: String,
    var thumbnail: String?,
    var imageList: List<Uri>?,
    var imageLink: String?
)