package com.filipibrentegani.marvelheroes.network

import com.filipibrentegani.marvelheroes.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory<IConnectionUtils> {
        ConnectionUtils(androidContext())
    }

    single {
        val okHttp = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttp.addInterceptor(logging)
        }
        okHttp.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_MARVEL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
}