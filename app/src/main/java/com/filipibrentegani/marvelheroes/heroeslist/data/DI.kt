package com.filipibrentegani.marvelheroes.heroeslist.data

import com.filipibrentegani.marvelheroes.heroeslist.domain.IFavoriteHeroesRepository
import com.filipibrentegani.marvelheroes.heroeslist.domain.IHeroesListRepository
import com.filipibrentegani.marvelheroes.network.MarvelApi
import com.filipibrentegani.marvelheroes.network.NetworkUtils
import org.koin.dsl.module
import retrofit2.Retrofit

val heroesListDataModule = module {
    factory {
        get<Retrofit>().create(MarvelApi::class.java)
    }

    factory<IHeroesListRepository> {
        HeroesListRepository(get())
    }

    factory<IFavoriteHeroesRepository> {
        FavoriteHeroesRepository(get())
    }
}