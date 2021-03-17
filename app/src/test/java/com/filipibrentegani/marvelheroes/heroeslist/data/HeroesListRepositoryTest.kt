package com.filipibrentegani.marvelheroes.heroeslist.data

import com.filipibrentegani.marvelheroes.entity.data.CharactersResponse
import com.filipibrentegani.marvelheroes.entity.data.DataResponse
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.filipibrentegani.marvelheroes.network.MarvelApi
import com.nhaarman.mockitokotlin2.any
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HeroesListRepositoryTest : BaseTest() {

    @Mock
    lateinit var apiMock: MarvelApi

    private val repository: HeroesListRepository by lazy {
        HeroesListRepository(
            apiMock
        )
    }

    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenRequestData_repositoryCallApiAndReturnData() {
        runBlockingTest {
            val listOfCharacters = listOf(getCharacterResponse())
            Mockito.`when`(apiMock.getCharacters(any(), any(), any(), any(), any(), any()))
                .thenReturn(
                    CharactersResponse(
                        DataResponse(
                            1,
                            1,
                            1,
                            1,
                            listOfCharacters
                        )
                    )
                )

            val result = repository.getHeroes(1, 1, "criteria")

            assertEquals(listOfCharacters, result)
        }
    }
}