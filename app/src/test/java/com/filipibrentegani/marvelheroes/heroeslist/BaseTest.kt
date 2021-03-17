package com.filipibrentegani.marvelheroes.heroeslist

import com.filipibrentegani.marvelheroes.entity.domain.Comic
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.network.ViewModelScopeProvider
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

open class BaseTest: KoinTest {
    @Before
    open fun setUp() {
        startKoin {
            modules(
                module {
                    factory {
                        ViewModelScopeProvider(
                            Dispatchers.Unconfined,
                            Dispatchers.Unconfined
                        )
                    }
                })
        }
    }

    @After
    open fun clean() {
        stopKoin()
    }

    fun getHero(heroId: Int): Hero {
        return Hero(heroId, "name", "description", "thumbnail", false, getComic(), getComic(), getComic(), getComic())
    }

    fun getComic(): Comic {
        return Comic(0, "", 1, listOf())
    }
}