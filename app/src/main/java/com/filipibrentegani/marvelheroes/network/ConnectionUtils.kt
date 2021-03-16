package com.filipibrentegani.marvelheroes.network

import android.content.Context
import android.net.ConnectivityManager

class ConnectionUtils(private val context: Context): IConnectionUtils {
    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}

interface IConnectionUtils {
    fun isConnected(): Boolean
}