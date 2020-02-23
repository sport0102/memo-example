package com.aiden.memo.data.database.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MemoTypeConverters {
    @TypeConverter
    fun jsonToStringList(json: String?): List<String?>? {
        return Gson().fromJson<List<String?>>(
            json,
            object : TypeToken<List<String?>?>() {}.type
        )
    }

    @TypeConverter
    fun stringListToJson(list: List<String>?): String? {
        return Gson().toJson(list)
    }

}