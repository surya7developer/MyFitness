package com.myfitness.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.myfitness.details.DetailsActivity
import com.myfitness.home.MainActivity
import com.myfitness.model.Results


fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun showLog(tag:String,msg:String){
    Log.d(tag,msg)
}

fun Context.showToast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun MainActivity.openDetailPage(result: Results) {

    startActivity(Intent(this,DetailsActivity::class.java)
        .putExtra(USER_OBJECT,gsonConverter(result = result))
    )
}


fun Context.isConnected(): Boolean {
    val result: Boolean
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return  result
}

fun Context.showSnackBar(layMain: ConstraintLayout) {
    Snackbar.make(layMain, "You're Offline", Snackbar.LENGTH_LONG)
        .setAction("Ok", { })
        .setActionTextColor(Color.WHITE)
        .setBackgroundTint(Color.BLACK)
        .setTextColor(Color.WHITE)
        .show()
}

fun Context.gsonConverter(result: Results) : String{
    val gson = Gson()
    return gson.toJson(result)
}

fun DetailsActivity.gsonToModel(): Results {
    return Gson().fromJson(intent.getStringExtra(USER_OBJECT), Results::class.java)
}


fun Context.openDialPad(phone:String){
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:".plus(phone))
    startActivity(intent)
}

fun Context.sendEmail(email:String){

    val emailIntent = Intent(
        Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", email, null
        )
    )
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Thins is my practical")
    startActivity(Intent.createChooser(emailIntent, null))

}
