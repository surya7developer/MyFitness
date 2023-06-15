package com.myfitness.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfitness.database.UserDatabase
import com.myfitness.databinding.ActivityMainBinding
import com.myfitness.home.diff.UserListDiffAdapter
import com.myfitness.home.viewmodel.MainViewModel
import com.myfitness.model.UserDataList
import com.myfitness.util.*

class MainActivity : AppCompatActivity() {

    val resultDefault = ArrayList<com.myfitness.model.Results>()
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var userListAdapter: UserListDiffAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewmodel()
        initialization()
        callAPIForUserData()
        observer()
        getWithPagination()


    }


    private fun observer() {

        mainViewModel.getUSerDataList().observe(this, Observer {

            if (it != null) {

                reloadAdapter(it)
                saveDataToRoom()
                pagination()
                manageVisibility()
            }
        })

    }

    private fun manageVisibility() {

        binding.apply {
            rvUserList.show()
            shimmerLayout.hide()
        }
    }

    private fun pagination() {
        mainViewModel.setPaging()

    }

    private fun saveDataToRoom() {
        mainViewModel.insert()
    }

    private fun reloadAdapter(it: UserDataList) {

        resultDefault.addAll(it.results)
        userListAdapter.submitList(resultDefault)

        binding.apply {
            rvUserList.apply {
                rvUserList.layoutManager?.onRestoreInstanceState(layoutManager?.onSaveInstanceState())
            }
        }
    }

    fun callAPIForUserData() {

        if (isConnected()) {
            mainViewModel.deleteLocalData()
            mainViewModel.getAllUserDataAPI()
        } else {
            mainViewModel.getDataFromLocal()
            showSnackBar(binding.layMain)
            binding.shimmerLayout.hide()
        }

    }

    private fun initViewmodel() {
        mainViewModel = ViewModelProvider(
            this,
            MainViewModel.MainViewModelFactory(this)
        )[MainViewModel::class.java]
        binding.mainActivity = this
    }

    private fun initialization() {

        userListAdapter = UserListDiffAdapter(this.mainViewModel)

        binding.apply {
            mLinearLayoutManager = LinearLayoutManager(this@MainActivity)

            userListAdapter.submitList(resultDefault)
            rvUserList.layoutManager = mLinearLayoutManager
            rvUserList.adapter = userListAdapter
        }
    }

    private fun getWithPagination() {
        binding.rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = mLinearLayoutManager.childCount
                    val totalItemCount = mLinearLayoutManager.itemCount
                    val firstVisibleItemPosition =
                        mLinearLayoutManager.findFirstVisibleItemPosition()

                    mainViewModel.apply {
                        if (isLoading.get()) {
                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= resultAtTime.value!!
                            ) {
                                isLoading.set(false)
                                getAllUserDataAPI()
                            }
                        }
                    }
                }
            }
        })
    }
}