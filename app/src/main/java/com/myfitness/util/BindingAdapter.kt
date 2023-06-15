package com.myfitness.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun ImageView.setUrl(url:String){

    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()


    Glide.with(this).load(url)
        .placeholder(circularProgressDrawable)
        .into(this)
}

@BindingAdapter("setDateFormatText")
fun TextView.setDateText(text:String){
    this.text = getDate(text)
}