package com.myfitness.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myfitness.model.Results
import com.myfitness.util.openDialPad
import com.myfitness.util.sendEmail

class DetailsViewModel(val context: DetailsActivity) : ViewModel() {

    lateinit var results: Results

    class DetailsViewModelFactory(val context: DetailsActivity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(context) as T
        }
    }

    fun openDialPad(str: String) {
        context.openDialPad(str)
    }

    fun emailRequest(email: String) {
        context.sendEmail(email)
    }

}