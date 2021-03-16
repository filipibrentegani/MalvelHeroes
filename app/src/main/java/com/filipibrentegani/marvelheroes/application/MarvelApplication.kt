package com.filipibrentegani.marvelheroes.application

import android.app.Application
import com.filipibrentegani.marvelheroes.heroesdetails.presentation.heroesDetailsPresentationModule
import com.filipibrentegani.marvelheroes.heroesfavorites.presentation.heroesFavoritePresentationModule
import com.filipibrentegani.marvelheroes.heroeslist.data.heroesListDataModule
import com.filipibrentegani.marvelheroes.heroeslist.domain.heroesListDomainModule
import com.filipibrentegani.marvelheroes.heroeslist.presentation.heroesListPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarvelApplication)

            modules(
                listOf(
                    heroesListPresentationModule,
                    generalModule,
                    heroesListDataModule,
                    heroesListDomainModule,
                    heroesFavoritePresentationModule,
                    heroesDetailsPresentationModule
                )
            )
        }
    }
}