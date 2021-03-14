package com.filipibrentegani.marvelheroes.network

import com.filipibrentegani.marvelheroes.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

object NetworkUtils {
    private fun okHttpClient(): OkHttpClient {
        val okHttp = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttp.addInterceptor(logging)
        }
        return okHttp.build()
    }

    fun <T> marvelRetrofitClient(contract: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_MARVEL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
        return retrofit.create(contract)
    }

    fun marvelHashCode(currentTimeMillis: Long): String {
        val md = MessageDigest.getInstance("MD5")
        val hash =
            md.digest("$currentTimeMillis${BuildConfig.PRIVATE_KEY_MARVEL_API}${BuildConfig.PUBLIC_KEY_MARVEL_API}".toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}