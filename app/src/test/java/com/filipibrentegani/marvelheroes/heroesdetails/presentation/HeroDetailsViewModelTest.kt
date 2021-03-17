package com.filipibrentegani.marvelheroes.heroesdetails.presentation

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.heroesfavorites.domain.IGetHeroUseCase
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.filipibrentegani.marvelheroes.heroeslist.domain.IChangeFavoriteStateUseCase
import com.filipibrentegani.marvelheroes.network.ResultWrapper
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HeroDetailsViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var changeFavoritesUseCaseMock: IChangeFavoriteStateUseCase

    @Mock
    lateinit var getHeroUseCaseMock: IGetHeroUseCase

    private val viewModel: HeroDetailsViewModel by lazy {
        HeroDetailsViewModel(
            getHeroUseCaseMock,
            changeFavoritesUseCaseMock
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenScreenReceiveANonFavoriteHero_thenViewModelSetTheScreenState() {
        runBlockingTest {
            Mockito.`when`(getHeroUseCaseMock.getFavoriteHero(1)).thenReturn(ResultWrapper.Success(getHero(1)))

            viewModel.setHero(1)

            assertEquals("name", viewModel.heroNameLiveData.value)
            assertEquals("thumbnail", viewModel.thumbnailLiveData.value)
            assertEquals("description", viewModel.descriptionLiveData.value)
            assertEquals(R.drawable.favorite_unchecked, viewModel.favoriteIconLiveData.value)
            assertEquals(
                R.string.btn_favorite_unchecked_content_description,
                viewModel.favoriteIconContentDescriptionLiveData.value
            )
        }
    }

    @Test
    fun whenScreenReceiveAFavoriteHero_thenViewModelSetTheScreenState() {
        runBlockingTest {
            val fakeHero = getHero(1)
            fakeHero.favorite = true
            Mockito.`when`(getHeroUseCaseMock.getFavoriteHero(1)).thenReturn(ResultWrapper.Success(fakeHero))

            viewModel.setHero(1)

            assertEquals("name", viewModel.heroNameLiveData.value)
            assertEquals("thumbnail", viewModel.thumbnailLiveData.value)
            assertEquals("description", viewModel.descriptionLiveData.value)
            assertEquals(R.drawable.favorite_checked, viewModel.favoriteIconLiveData.value)
            assertEquals(
                R.string.btn_favorite_checked_content_description,
                viewModel.favoriteIconContentDescriptionLiveData.value
            )
        }
    }

    @Test
    fun whenScreenReceiveAFavoriteHero_andReceiveAnError_thenViewModelSetTheScreenState() {
        runBlockingTest {
            Mockito.`when`(getHeroUseCaseMock.getFavoriteHero(1)).thenReturn(ResultWrapper.Error(Throwable("error")))

            viewModel.setHero(1)

            assertTrue(viewModel.showErrorLiveData.value?.first ?: false)
            assertEquals("error", viewModel.showErrorLiveData.value?.second)
        }
    }

    @Test
    fun whenUserWantsChangeFavoriteState_thenViewModelCallUseCaseAndUpdateScreenState() {
        runBlockingTest {
            val fakeHero = getHero(1)
            Mockito.`when`(getHeroUseCaseMock.getFavoriteHero(1)).thenReturn(ResultWrapper.Success(fakeHero))

            viewModel.setHero(1)
            viewModel.changeFavoriteState()

            assertEquals("name", viewModel.heroNameLiveData.value)
            assertEquals("thumbnail", viewModel.thumbnailLiveData.value)
            assertEquals("description", viewModel.descriptionLiveData.value)
            assertEquals(R.drawable.favorite_checked, viewModel.favoriteIconLiveData.value)
            assertEquals(
                R.string.btn_favorite_checked_content_description,
                viewModel.favoriteIconContentDescriptionLiveData.value
            )
            assertTrue(fakeHero.favorite)
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