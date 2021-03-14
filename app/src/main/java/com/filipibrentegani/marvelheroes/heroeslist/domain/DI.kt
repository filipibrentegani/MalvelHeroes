package com.filipibrentegani.marvelheroes.heroeslist.domain

import org.koin.dsl.module

val heroesListDomainModule = module {
    factory {
        HeroesListUseCase(get(), get())
    }
}