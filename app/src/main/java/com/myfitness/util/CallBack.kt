package com.myfitness.util

import com.myfitness.api.RetrofitHelper
import com.myfitness.api.Service

fun getCall(): Service {
    return RetrofitHelper.getRetrofit().create(Service::class.java)
}