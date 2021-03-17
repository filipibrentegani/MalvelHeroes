package com.filipibrentegani.marvelheroes.heroesdetails.presentation

import android.app.Activity
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.entity.domain.Story
import com.filipibrentegani.marvelheroes.heroesfavorites.domain.IGetFavoriteHeroUseCase
import com.filipibrentegani.marvelheroes.heroeslist.domain.IChangeFavoriteStateUseCase
import com.filipibrentegani.marvelheroes.utils.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroDetailsViewModel(
    private val getFavoriteHeroUseCase: IGetFavoriteHeroUseCase,
    private val useCase: IChangeFavoriteStateUseCase
) : BaseViewModel() {

    private val heroName = MutableLiveData<String>()
    val heroNameLiveData: LiveData<String> = heroName
    private val thumbnail = MutableLiveData<String>()
    val thumbnailLiveData: LiveData<String> = thumbnail
    private val description = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String> = description
    private val comics = MutableLiveData<List<Story>>()
    val comicsLiveData: LiveData<List<Story>> = comics
    private val series = MutableLiveData<List<Story>>()
    val seriesLiveData: LiveData<List<Story>> = series
    private val stories = MutableLiveData<List<Story>>()
    val storiesLiveData: LiveData<List<Story>> = stories
    private val events = MutableLiveData<List<Story>>()
    val eventsLiveData: LiveData<List<Story>> = events
    private val favoriteIcon = MutableLiveData<Int>()
    val favoriteIconLiveData: LiveData<Int> = favoriteIcon
    private val favoriteIconContentDescription = MutableLiveData<Int>()
    val favoriteIconContentDescriptionLiveData: LiveData<Int> = favoriteIconContentDescription
    private lateinit var hero: Hero
    @VisibleForTesting var favoriteStateWasChanged = false

    fun setHero(heroId: Int) {
        viewModelScope.launch(scopes.ioScope) {
            getFavoriteHeroUseCase.getFavoriteHero(heroId)?.let {
                hero = it
            }
            withContext(scopes.uiScope) {
                heroName.value = hero.name
                thumbnail.value = hero.thumbnail
                description.value = hero.description
                comics.value = hero.comics.items
                series.value = hero.series.items
                stories.value = hero.stories.items
                events.value = hero.events.items
                updateFavoriteState()
            }
        }
    }

    fun changeFavoriteState() {
        favoriteStateWasChanged = true
        viewModelScope.launch(scopes.ioScope) {
            hero.favorite = !hero.favorite
            useCase.changeFavoriteState(hero)
            withContext(scopes.uiScope) {
                updateFavoriteState()
            }
        }
    }

    private fun updateFavoriteState() {
        favoriteIcon.value =
            if (hero.favorite) R.drawable.favorite_checked else R.drawable.favorite_unchecked
        favoriteIconContentDescription.value =
            if (hero.favorite)
                R.string.btn_favorite_checked_content_description
            else
                R.string.btn_favorite_unchecked_content_description
    }

    fun activityResult(): Int {
        return if (favoriteStateWasChanged) Activity.RESULT_OK else Activity.RESULT_CANCELED
    }
}