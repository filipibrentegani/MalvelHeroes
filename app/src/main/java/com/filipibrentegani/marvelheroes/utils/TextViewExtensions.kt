package com.filipibrentegani.marvelheroes.utils

import android.view.View
import android.widget.TextView

fun TextView.setTextOrGone(newText: String?) {
    newText?.let {
        if (it.isNotEmpty()) {
            text = it
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    } ?: run {
        visibility = View.GONE
    }
}