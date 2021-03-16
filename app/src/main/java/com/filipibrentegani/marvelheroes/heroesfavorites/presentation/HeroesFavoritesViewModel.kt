package com.filipibrentegani.marvelheroes.heroesfavorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.domain.HeroesListUseCase
import com.filipibrentegani.marvelheroes.utils.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroesFavoritesViewModel(private val useCase: HeroesListUseCase) : BaseViewModel() {

    private val heroes = MutableLiveData<List<Hero>>()
    val heroesLiveData: LiveData<List<Hero>> = heroes
    private val showHasNoFavorites = MutableLiveData<Boolean>()
    val showHasNoFavoritesLiveData: LiveData<Boolean> = showHasNoFavorites
    private val showLoading = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean> = showLoading

    fun showFavoriteHeroes() {
        showLoading.value = true
        viewModelScope.launch(scopes.ioScope) {
            val heroesList = useCase.getFavoriteHeroes()

            withContext(scopes.uiScope) {
                showLoading.value = false
                if (heroesList.isEmpty()) {
                    showHasNoFavorites.value = true
                } else {
                    heroes.value = heroesList
                }
            }
        }
    }

    fun removeFavoriteHero(hero: Hero) {
        viewModelScope.launch(scopes.ioScope) {
            useCase.changeFavoriteState(hero)
        }
    }
}