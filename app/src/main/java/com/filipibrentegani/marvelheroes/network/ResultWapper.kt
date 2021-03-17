package com.filipibrentegani.marvelheroes.network

sealed class ResultWrapper<out T: Any, out E: Throwable>() {
    abstract val successValue: T?
    abstract val errorValue: E?

    class Success<out T: Any, out E: Throwable>(private val value: T) : ResultWrapper<T, E>() {
        override val successValue: T
            get() = value
        override val errorValue: E?
            get() = null
    }
    class Error<out T: Any, out E: Throwable>(private val error: E) : ResultWrapper<T, E>() {
        override val successValue: T?
            get() = null
        override val errorValue: E?
            get() = error
    }
}