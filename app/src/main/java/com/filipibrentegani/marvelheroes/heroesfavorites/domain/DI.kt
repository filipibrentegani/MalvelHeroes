package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import org.koin.dsl.module

val heroesFavoritesDomainModule = module {
    factory<IGetHeroUseCase> {
        GetHeroUseCase(get(), get(), get())
    }
}