package com.filipibrentegani.marvelheroes.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ViewModelScopeProvider(
    uiScope: CoroutineDispatcher = Dispatchers.Main,
    ioScope: CoroutineDispatcher = Dispatchers.IO
)