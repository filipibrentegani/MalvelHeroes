package com.filipibrentegani.marvelheroes.heroeslist.presentation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val heroesListPresentationModule = module {
    viewModel {
        HeroesListViewModel(get(), get())
    }

    factory {
        HeroesListAdapter()
    }

    factory {
        HeroesPagingSource(get())
    }
}