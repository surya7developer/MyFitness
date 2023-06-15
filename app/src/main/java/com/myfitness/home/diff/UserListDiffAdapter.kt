package com.myfitness.home.diff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myfitness.databinding.UserListItemBinding
import com.myfitness.home.viewmodel.MainViewModel
import com.myfitness.model.Results

class UserListDiffAdapter(val mainViewModel: MainViewModel) :
    ListAdapter<Results, UserListDiffAdapter.ViewHolder>(DiffUtil()) {

    lateinit var binding: UserListItemBinding

    class ViewHolder(view: UserListItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.mainViewModel = mainViewModel
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        binding.apply {
            modelResult = getItem(position)
        }
    }


    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {

            return oldItem == newItem
        }
    }

}