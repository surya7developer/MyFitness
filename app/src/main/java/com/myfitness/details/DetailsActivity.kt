package com.myfitness.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.myfitness.databinding.ActivityDetailsBinding
import com.myfitness.util.gsonToModel
import com.myfitness.util.show

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        manageToolBar()
        initViewmodel()
    }

    private fun initViewmodel() {
        detailsViewModel = ViewModelProvider(
            this,
            DetailsViewModel.DetailsViewModelFactory(this)
        )[DetailsViewModel::class.java]
        detailsViewModel.results = gsonToModel()
        binding.detailViewModel = detailsViewModel
        binding.layTitle.activity = this

    }

    private fun manageToolBar() {
        binding.apply {
            layTitle.btnBackArrow.show()
        }
    }
}