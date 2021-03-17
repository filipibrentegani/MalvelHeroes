package com.filipibrentegani.marvelheroes.heroesdetails.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.filipibrentegani.marvelheroes.heroeslist.domain.IChangeFavoriteStateUseCase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HeroDetailsViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var changeFavoritesUseCaseMockUseCase: IChangeFavoriteStateUseCase

    private val viewModel: HeroDetailsViewModel by lazy {
        HeroDetailsViewModel(
            changeFavoritesUseCaseMockUseCase
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenScreenReceiveANonFavoriteHero_thenViewModelSetTheScreenState() {
        viewModel.setHero(getHero(1))

        assertEquals("name", viewModel.heroNameLiveData.value)
        assertEquals("thumbnail", viewModel.thumbnailLiveData.value)
        assertEquals("description", viewModel.descriptionLiveData.value)
        assertEquals(R.drawable.favorite_unchecked, viewModel.favoriteIconLiveData.value)
        assertEquals(
            R.string.btn_favorite_unchecked_content_description,
            viewModel.favoriteIconContentDescriptionLiveData.value
        )
    }

    @Test
    fun whenScreenReceiveAFavoriteHero_thenViewModelSetTheScreenState() {
        val fakeHero = getHero(1)
        fakeHero.favorite = true

        viewModel.setHero(fakeHero)

        assertEquals("name", viewModel.heroNameLiveData.value)
        assertEquals("thumbnail", viewModel.thumbnailLiveData.value)
        assertEquals("description", viewModel.descriptionLiveData.value)
        assertEquals(R.drawable.favorite_checked, viewModel.favoriteIconLiveData.value)
        assertEquals(
            R.string.btn_favorite_checked_content_description,
            viewModel.favoriteIconContentDescriptionLiveData.value
        )
    }

    @Test
    fun whenUserWantsChangeFavoriteState_thenViewModelCallUseCaseAndUpdateScreenState() {
        val fakeHero = getHero(1)

        viewModel.setHero(fakeHero)
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