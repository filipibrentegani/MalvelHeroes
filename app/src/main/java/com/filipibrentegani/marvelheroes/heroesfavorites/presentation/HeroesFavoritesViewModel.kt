package com.filipibrentegani.marvelheroes.heroesfavorites.presentation

import android.view.View
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
    private val hasNoFavorites = MutableLiveData<Int>()
    val hasNoFavoritesLiveData: LiveData<Int> = hasNoFavorites
    private val showLoading = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean> = showLoading

    fun showFavoriteHeroes() {
        showLoading.value = true
        viewModelScope.launch(scopes.ioScope) {
            val heroesList = useCase.getFavoriteHeroes()

            withContext(scopes.uiScope) {
                showLoading.value = false
                heroes.value = heroesList
                hasNoFavorites.value = if (heroesList.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    fun removeFavoriteHero(hero: Hero) {
        viewModelScope.launch(scopes.ioScope) {
            useCase.changeFavoriteState(hero)
        }
    }
}