package com.example.timerecord.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapterUtil {


    @JvmStatic
    @BindingAdapter("app:showSelectedTime")
    fun showSelectedTime(view: View, value: String?) {
        if(value.isNullOrEmpty()) (view as TextView).text = "Not Yet"
        else (view as TextView).text = value
    }


}