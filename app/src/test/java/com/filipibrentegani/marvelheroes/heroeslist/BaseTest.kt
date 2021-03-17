package com.filipibrentegani.marvelheroes.heroeslist

import com.filipibrentegani.marvelheroes.entity.data.CharacterResponse
import com.filipibrentegani.marvelheroes.entity.data.ComicItemResponse
import com.filipibrentegani.marvelheroes.entity.data.ComicResponse
import com.filipibrentegani.marvelheroes.entity.data.ThumbnailResponse
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

    fun getCharacterResponse(): CharacterResponse {
        return CharacterResponse(
            1,
            "name",
            "description",
            ThumbnailResponse("http://thumbnail", "jpg"),
            "resourceURI",
            getComicResponse(),
            getComicResponse(),
            getComicResponse(),
            getComicResponse()
        )
    }

    private fun getComicResponse(): ComicResponse {
        return ComicResponse(
            1,
            "collectionUrl",
            listOf(getComicItemResponse(), getComicItemResponse("type"))
        )
    }

    private fun getComicItemResponse(type: String? = null): ComicItemResponse {
        return ComicItemResponse("resourceURI", "name", type)
    }
}