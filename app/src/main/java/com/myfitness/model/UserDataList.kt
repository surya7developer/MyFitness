package com.myfitness.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserDataList")
data class UserDataList(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var results: List<Results>
)