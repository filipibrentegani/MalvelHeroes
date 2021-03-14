package com.filipibrentegani.marvelheroes.application

import androidx.room.Room
import com.filipibrentegani.marvelheroes.localpersistence.Database
import com.filipibrentegani.marvelheroes.network.ViewModelScopeProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val generalModule = module {
    single {
        ViewModelScopeProvider()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java, "database"
        ).build().heroDao()
    }
}