package com.filipibrentegani.marvelheroes.heroeslist.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.nhaarman.mockitokotlin2.any
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ChangeFavoriteStateUseCaseTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repositoryFavoritesMock: IFavoriteHeroesRepository

    private val useCase: ChangeFavoriteStateUseCase by lazy {
        ChangeFavoriteStateUseCase(repositoryFavoritesMock)
    }

    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenRequestToChangeStateOfAFavorite_UseCaseRemovesFromCache() {
        val fakeHero = getHero(1)
        fakeHero.favorite = true

        runBlockingTest {
            useCase.changeFavoriteState(fakeHero)

            Mockito.verify(repositoryFavoritesMock).addFavoriteHeroes(fakeHero)
        }
    }

    @Test
    fun whenRequestToChangeStateOfNonFavorite_UseCaseAddToCache() {
        val fakeHero = getHero(1)
        fakeHero.favorite = false

        runBlockingTest {
            useCase.changeFavoriteState(fakeHero)

            Mockito.verify(repositoryFavoritesMock).removeFavoriteHero(fakeHero)
        }
    }
}