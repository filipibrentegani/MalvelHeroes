package com.filipibrentegani.marvelheroes.application

import com.filipibrentegani.marvelheroes.network.ViewModelScopeProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val generalModule = module {
    single {
        ViewModelScopeProvider()
    }
}