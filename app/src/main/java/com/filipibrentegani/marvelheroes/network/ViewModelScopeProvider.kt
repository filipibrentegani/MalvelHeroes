package com.filipibrentegani.marvelheroes.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ViewModelScopeProvider(
    val uiScope: CoroutineDispatcher = Dispatchers.Main,
    val ioScope: CoroutineDispatcher = Dispatchers.IO
)