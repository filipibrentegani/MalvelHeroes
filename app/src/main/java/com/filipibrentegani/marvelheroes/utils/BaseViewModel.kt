package com.filipibrentegani.marvelheroes.utils

import androidx.lifecycle.ViewModel
import com.filipibrentegani.marvelheroes.network.ViewModelScopeProvider
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel: ViewModel(), KoinComponent {
    protected val scopes: ViewModelScopeProvider by inject()
}