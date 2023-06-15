package com.myfitness.home.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.myfitness.database.UserDatabase
import com.myfitness.home.MainActivity
import com.myfitness.model.Results
import com.myfitness.model.UserDataList
import com.myfitness.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val mainActivity: MainActivity) : ViewModel() {


    private lateinit var database: UserDatabase
    val isLoading = ObservableBoolean(true)
    val page: MutableLiveData<Int> = MutableLiveData(1)
    val resultAtTime: MutableLiveData<Int> = MutableLiveData(10)

    private val userDataList: MutableLiveData<UserDataList> = MutableLiveData()
    val liveData: LiveData<UserDataList> get() = userDataList

    fun getUSerDataList(): LiveData<UserDataList> {
        return liveData
    }


    fun getAllUserDataAPI() {
        val call = getCall().getUSerDataList(page.value, resultAtTime.value)
        viewModelScope.launch(Dispatchers.IO) {

            call.enqueue(object : Callback<UserDataList> {
                override fun onResponse(
                    call: Call<UserDataList>,
                    response: Response<UserDataList>,
                ) {
                    userDataList.value = response.body()
                    database = UserDatabase.getDatabase(mainActivity)
                }

                override fun onFailure(call: Call<UserDataList>, t: Throwable) {
                    showLog(API_RESULT_TAG, "onFailure = ${t.localizedMessage}")
                }
            })
        }
    }

    fun getDataFromLocal() {
        database = UserDatabase.getDatabase(mainActivity)
        database.DoaService().getUserListDatabase().observe(mainActivity) {

            if (it != null) {
                userDataList.value = it
            }
        }

    }

    fun openDetail(result: Results) {
        mainActivity.openDetailPage(result)
    }

    fun openDialPad(str: String) {
        mainActivity.openDialPad(str)
    }

    fun emailRequest(email: String) {
        mainActivity.sendEmail(email)
    }


    fun insert() {
        database = UserDatabase.getDatabase(mainActivity)
        MainScope().launch {
            getUSerDataList().value?.let { database.DoaService().insertUsrList(it) }
        }
    }

    fun setPaging() {
        page.value = page.value?.plus(1)
        isLoading.set(true)
    }

    fun deleteLocalData() {
        database = UserDatabase.getDatabase(mainActivity)
        MainScope().launch {
            getUSerDataList().value?.let { database.DoaService().deletePreviousData() }
        }
    }


    class MainViewModelFactory(val context: MainActivity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(context) as T
        }
    }


}