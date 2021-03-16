package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import org.koin.dsl.module

val heroesFavoritesDomainModule = module {
    factory<IGetFavoriteHeroesUseCase> {
        GetFavoriteHeroesUseCase(get())
    }
}