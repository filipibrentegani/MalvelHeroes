package com.filipibrentegani.marvelheroes.application

import android.app.Application
import com.filipibrentegani.marvelheroes.heroeslist.presentation.heroesListPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarvelApplication)

            modules(listOf(heroesListPresentationModule, generalModule))
        }
    }
}