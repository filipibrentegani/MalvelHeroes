package com.filipibrentegani.marvelheroes.heroesfavorites.presentation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val heroesFavoritePresentationModule = module {
    viewModel {
        HeroesFavoritesViewModel(get())
    }

    factory {
        HeroesFavoritesAdapter()
    }
}