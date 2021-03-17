package com.filipibrentegani.marvelheroes.heroesfavorites.presentation

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.filipibrentegani.marvelheroes.heroeslist.domain.IChangeFavoriteStateUseCase
import com.filipibrentegani.marvelheroes.heroeslist.domain.IGetFavoriteHeroesUseCase
import junit.framework.Assert.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HeroesFavoritesViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var useCaseFavoriteMockUseCase: IGetFavoriteHeroesUseCase

    @Mock
    lateinit var useCaseChangeFavoriteStateUseCaseMock: IChangeFavoriteStateUseCase

    private val viewModel: HeroesFavoritesViewModel by lazy {
        HeroesFavoritesViewModel(
            useCaseFavoriteMockUseCase,
            useCaseChangeFavoriteStateUseCaseMock
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenShowFavoriteHeroes_thenViewModelSetScreenState() {
        val heroList = listOf(getHero(1), getHero(2), getHero(3))
        runBlockingTest {
            Mockito.`when`(useCaseFavoriteMockUseCase.getFavoriteHeroes()).thenReturn(heroList)

            viewModel.showFavoriteHeroes()

            assertFalse(viewModel.showLoadingLiveData.value ?: true)
            assertFalse(viewModel.showHasNoFavoritesLiveData.value ?: true)
            assertEquals(3, viewModel.heroesLiveData.value?.count() ?: 0)
        }
    }

    @Test
    fun whenShowFavoriteHeroes_thenViewModelSetEmptyScreenState() {
        val heroList = listOf<Hero>()
        runBlockingTest {
            Mockito.`when`(useCaseFavoriteMockUseCase.getFavoriteHeroes()).thenReturn(heroList)

            viewModel.showFavoriteHeroes()

            assertFalse(viewModel.showLoadingLiveData.value ?: true)
            assertTrue(viewModel.showHasNoFavoritesLiveData.value ?: false)
            assertNull(viewModel.heroesLiveData.value)
        }
    }

    @Test
    fun whenRemoveFavoriteHero_thenViewModelRequestForUseCase() {
        runBlockingTest {
            val fakeHero = getHero(1)

            viewModel.removeFavoriteHero(fakeHero)

            Mockito.verify(useCaseChangeFavoriteStateUseCaseMock).changeFavoriteState(fakeHero)
        }
    }

    @Test
    fun whenScreenWillReturnResult_thenFavoriteStateWasChanged() {
        runBlockingTest {
            viewModel.favoriteStateWasChanged = true

            val activityResult = viewModel.activityResult()

            assertEquals(Activity.RESULT_OK, activityResult)
        }
    }

    @Test
    fun whenScreenWillReturnResult_thenFavoriteStateWasNotChanged() {
        runBlockingTest {
            viewModel.favoriteStateWasChanged = false

            val activityResult = viewModel.activityResult()

            assertEquals(Activity.RESULT_CANCELED, activityResult)
        }
    }
}