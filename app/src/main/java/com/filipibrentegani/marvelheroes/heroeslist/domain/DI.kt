package com.filipibrentegani.marvelheroes.heroeslist.domain

import org.koin.dsl.module

val heroesListDomainModule = module {
    factory<IGetHeroesUseCase> {
        GetHeroesUseCase(get(), get())
    }

    factory<IChangeFavoriteStateUseCase> {
        ChangeFavoriteStateUseCase(get())
    }

    factory<IGetFavoriteHeroesUseCase> {
        GetFavoriteHeroesUseCase(
            get()
        )
    }
}