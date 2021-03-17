package com.filipibrentegani.marvelheroes.heroesdetails.presentation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val heroesDetailsPresentationModule = module {
    viewModel {
        HeroDetailsViewModel(get(), get())
    }
}