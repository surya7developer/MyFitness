package com.myfitness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myfitness.model.UserDataList

@Dao
interface DoaService {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsrList(userDataList: UserDataList)

    @Query("SELECT * FROM UserDataList")
    fun getUserListDatabase() : LiveData<UserDataList>

    @Query("DELETE FROM UserDataList")
    fun deletePreviousData()
}