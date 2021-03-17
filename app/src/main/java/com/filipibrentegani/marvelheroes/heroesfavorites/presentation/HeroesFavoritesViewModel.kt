package com.filipibrentegani.marvelheroes.heroesfavorites.presentation

import android.app.Activity
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.domain.IChangeFavoriteStateUseCase
import com.filipibrentegani.marvelheroes.heroeslist.domain.IGetFavoriteHeroesUseCase
import com.filipibrentegani.marvelheroes.utils.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroesFavoritesViewModel(
    private val useCaseFavoriteUseCase: IGetFavoriteHeroesUseCase,
    private val useCaseChangeFavoriteStateUseCase: IChangeFavoriteStateUseCase
) : BaseViewModel() {

    private val heroes = MutableLiveData<List<Hero>>()
    val heroesLiveData: LiveData<List<Hero>> = heroes
    private val showHasNoFavorites = MutableLiveData<Boolean>()
    val showHasNoFavoritesLiveData: LiveData<Boolean> = showHasNoFavorites
    private val showLoading = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean> = showLoading
    @VisibleForTesting
    var favoriteStateWasChanged = false

    fun showFavoriteHeroes() {
        showLoading.value = true
        viewModelScope.launch(scopes.ioScope) {
            val heroesList = useCaseFavoriteUseCase.getFavoriteHeroes()

            withContext(scopes.uiScope) {
                showLoading.value = false
                if (heroesList.isEmpty()) {
                    showHasNoFavorites.value = true
                } else {
                    heroes.value = heroesList
                    showHasNoFavorites.value = false
                }
            }
        }
    }

    fun removeFavoriteHero(hero: Hero) {
        favoriteStateWasChanged = true
        viewModelScope.launch(scopes.ioScope) {
            useCaseChangeFavoriteStateUseCase.changeFavoriteState(hero)
        }
    }

    fun activityResult(): Int {
        return if (favoriteStateWasChanged) Activity.RESULT_OK else Activity.RESULT_CANCELED
    }
}