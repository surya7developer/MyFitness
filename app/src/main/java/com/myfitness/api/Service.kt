package com.myfitness.api

import com.myfitness.model.UserDataList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("api/")
    fun getUSerDataList(
        @Query("page") page: Int?,
        @Query("results") results: Int?,
    ) : Call<UserDataList>

}