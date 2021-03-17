package com.filipibrentegani.marvelheroes.heroeslist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.filipibrentegani.marvelheroes.heroeslist.domain.IChangeFavoriteStateUseCase
import com.filipibrentegani.marvelheroes.heroeslist.domain.IGetHeroesUseCase
import com.filipibrentegani.marvelheroes.network.IConnectionUtils
import junit.framework.Assert.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HeroesListViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getHeroesUseCaseMock: IGetHeroesUseCase

    @Mock
    lateinit var changeFavoriteStateUseCaseUseCaseMock: IChangeFavoriteStateUseCase

    @Mock
    lateinit var connectionsUtilsMock: IConnectionUtils

    private val viewModel: HeroesListViewModel by lazy {
        HeroesListViewModel(
            getHeroesUseCaseMock,
            changeFavoriteStateUseCaseUseCaseMock,
            connectionsUtilsMock
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenPagingDataReturnsLoadingState_thenViewModelSetScreenState() {
        runBlockingTest {
            viewModel.showListState(
                CombinedLoadStates(
                    LoadState.Loading,
                    LoadState.Loading,
                    LoadState.Loading,
                    LoadStates(LoadState.Loading, LoadState.Loading, LoadState.Loading)
                ), 0
            )

            assertTrue(viewModel.showLoadingLiveData.value ?: false)
            assertFalse(viewModel.showEmptyStateLiveData.value ?: true)
            assertFalse(viewModel.showErrorLiveData.value?.first ?: true)
            assertFalse(viewModel.showListLiveData.value ?: true)
        }
    }

    @Test
    fun whenPagingDataReturnsNoResults_thenViewModelSetScreenState() {
        runBlockingTest {
            viewModel.showListState(
                CombinedLoadStates(
                    LoadState.Loading,
                    LoadState.Loading,
                    LoadState.NotLoading(true),
                    LoadStates(LoadState.NotLoading(true), LoadState.Loading, LoadState.Loading)
                ), 0
            )

            assertFalse(viewModel.showLoadingLiveData.value ?: true)
            assertTrue(viewModel.showEmptyStateLiveData.value ?: true)
            assertFalse(viewModel.showErrorLiveData.value?.first ?: true)
            assertFalse(viewModel.showListLiveData.value ?: true)
        }
    }

    @Test
    fun whenPagingDataReturnsError_thenViewModelSetScreenState() {
        runBlockingTest {
            viewModel.showListState(
                CombinedLoadStates(
                    LoadState.Loading, LoadState.Loading, LoadState.Loading, LoadStates(
                        LoadState.Error(
                            Throwable("error")
                        ), LoadState.Loading, LoadState.Loading
                    )
                ), 0
            )

            assertFalse(viewModel.showLoadingLiveData.value ?: true)
            assertFalse(viewModel.showEmptyStateLiveData.value ?: true)
            assertTrue(viewModel.showErrorLiveData.value?.first ?: false)
            assertEquals(viewModel.showErrorLiveData.value?.second, "error")
            assertFalse(viewModel.showListLiveData.value ?: true)
        }
    }

    @Test
    fun whenPagingDataReturnsValues_thenViewModelSetScreenState() {
        runBlockingTest {
            viewModel.showListState(
                CombinedLoadStates(
                    LoadState.NotLoading(false), LoadState.Loading, LoadState.Loading, LoadStates(
                        LoadState.NotLoading(false), LoadState.Loading, LoadState.Loading
                    )
                ), 0
            )

            assertFalse(viewModel.showLoadingLiveData.value ?: true)
            assertFalse(viewModel.showEmptyStateLiveData.value ?: true)
            assertFalse(viewModel.showErrorLiveData.value?.first ?: true)
            assertTrue(viewModel.showListLiveData.value ?: false)
        }
    }

    @Test
    fun whenUserCallToChangeFavoriteState_thenViewModelCallsUseCaseChangingFavoriteValue() {
        val fakeHero = Hero(1, "n", "d", "t", false, getComic(), getComic(), getComic(), getComic())

        runBlockingTest {
            viewModel.changeFavoriteState(fakeHero)

            Mockito.verify(changeFavoriteStateUseCaseUseCaseMock).changeFavoriteState(fakeHero)
        }
    }
}