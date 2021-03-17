package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.filipibrentegani.marvelheroes.heroeslist.domain.IFavoriteHeroesRepository
import junit.framework.Assert.assertEquals
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

    private val useCase: GetFavoriteHeroUseCase by lazy {
        GetFavoriteHeroUseCase(
            repositoryFavoritesMock
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
            Mockito.`when`(repositoryFavoritesMock.getFavoriteHero(1)).thenReturn(fakeHero)

            val hero = useCase.getFavoriteHero(1)

            assertEquals(fakeHero, hero)
        }
    }
}