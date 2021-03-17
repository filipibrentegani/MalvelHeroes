package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.filipibrentegani.marvelheroes.heroeslist.domain.IFavoriteHeroesRepository
import com.filipibrentegani.marvelheroes.heroeslist.domain.IHeroesListRepository
import com.filipibrentegani.marvelheroes.network.IConnectionUtils
import com.filipibrentegani.marvelheroes.network.ResultWrapper
import com.nhaarman.mockitokotlin2.any
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetFavoriteHeroUseCaseTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repositoryFavoritesMock: IFavoriteHeroesRepository
    @Mock
    lateinit var repositoryMock: IHeroesListRepository
    @Mock
    lateinit var connectionUtilsMock: IConnectionUtils

    private val useCase: GetHeroUseCase by lazy {
        GetHeroUseCase(
            repositoryMock,
            repositoryFavoritesMock,
            connectionUtilsMock
        )
    }

    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenRequestASpecificHero_useCaseCallForRepositoryAndReturnValue() {
        runBlockingTest {
            val fakeHero = getHero(1)
            Mockito.`when`(repositoryFavoritesMock.getFavoriteHeroes()).thenReturn(listOf(fakeHero))
            Mockito.`when`(connectionUtilsMock.isConnected()).thenReturn(true)
            Mockito.`when`(repositoryMock.getHero(any())).thenReturn(getCharacterResponse())

            val result = useCase.getFavoriteHero(1)

            assertEquals("https://thumbnail.jpg", result.successValue?.thumbnail)
            assertEquals("name", result.successValue?.name)
        }
    }

    @Test
    fun whenRequestASpecificHeroWithoutInternet_useCaseCallForRepositoryAndReturnValue() {
        runBlockingTest {
            Mockito.`when`(connectionUtilsMock.isConnected()).thenReturn(false)

            val result = useCase.getFavoriteHero(1)

            assertTrue(result is ResultWrapper.Error)
        }
    }
}