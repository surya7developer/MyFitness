package com.myfitness.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myfitness.model.*
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun fromString(value: String?): List<Results> {
        val listType: Type = object : TypeToken<List<Results>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Results>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}