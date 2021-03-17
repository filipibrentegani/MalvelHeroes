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

class GetFavoriteHeroesUseCaseTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repositoryFavoritesMock: IFavoriteHeroesRepository

    private val useCase: GetFavoriteHeroesUseCase by lazy {
        GetFavoriteHeroesUseCase(
            repositoryFavoritesMock
        )
    }

    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenCallForFavoriteHeroesList_useCaseOnlyCallRepository() {
        runBlockingTest {
            val favoriteHeroesFakeList = listOf(getHero(1))
            Mockito.`when`(repositoryFavoritesMock.getFavoriteHeroes()).thenReturn(favoriteHeroesFakeList)

            val heroList = useCase.getFavoriteHeroes()

            assertEquals(favoriteHeroesFakeList, heroList)
        }
    }
}