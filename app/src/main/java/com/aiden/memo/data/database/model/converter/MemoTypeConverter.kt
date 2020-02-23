package com.aiden.memo.data.database.model.converter

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MemoTypeConverters {
    @TypeConverter
    fun jsonToList(json: String?): List<Uri?>? {
        return Gson().fromJson<List<Uri?>>(
            json,
            object : TypeToken<List<Uri?>?>() {}.type
        )
    }

    @TypeConverter
    fun listToJson(list: List<Uri?>?): String? {
        return Gson().toJson(list)
    }
}