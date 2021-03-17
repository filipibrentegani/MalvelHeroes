package com.filipibrentegani.marvelheroes.heroeslist.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipibrentegani.marvelheroes.entity.data.CharacterResponse
import com.filipibrentegani.marvelheroes.entity.data.ComicItemResponse
import com.filipibrentegani.marvelheroes.entity.data.ComicResponse
import com.filipibrentegani.marvelheroes.entity.data.ThumbnailResponse
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.nhaarman.mockitokotlin2.any
import junit.framework.Assert.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetHeroesUseCaseTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repositoryListMock: IHeroesListRepository

    @Mock
    lateinit var repositoryFavoritesMock: IFavoriteHeroesRepository

    private val useCase: GetHeroesUseCase by lazy {
        GetHeroesUseCase(
            repositoryListMock,
            repositoryFavoritesMock
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenRequestTheHeroesList_useCaseMergeTheApiAndLocalData() {
        val fakeHero = getHero(1)
        fakeHero.favorite = true

        runBlockingTest {
            Mockito.`when`(repositoryListMock.getHeroes(any(), any(), any()))
                .thenReturn(listOf(getCharacterResponse()))
            Mockito.`when`(repositoryFavoritesMock.getFavoriteHeroes())
                .thenReturn(listOf(fakeHero))

            val listHeroes = useCase.getHeroes(1, 1, "criteria")

            assertEquals(1, listHeroes.size)
            assertEquals("https://thumbnail.jpg", listHeroes[0].thumbnail)
            assertTrue(listHeroes[0].favorite)
        }
    }

    @Test
    fun whenRequestTheHeroesList_useCaseOnlyTransformApiData() {
        runBlockingTest {
            Mockito.`when`(repositoryListMock.getHeroes(any(), any(), any()))
                .thenReturn(listOf(getCharacterResponse()))
            Mockito.`when`(repositoryFavoritesMock.getFavoriteHeroes())
                .thenReturn(listOf())

            val listHeroes = useCase.getHeroes(1, 1, "criteria")

            assertEquals(1, listHeroes.size)
            assertEquals("https://thumbnail.jpg", listHeroes[0].thumbnail)
            assertFalse(listHeroes[0].favorite)
        }
    }
}