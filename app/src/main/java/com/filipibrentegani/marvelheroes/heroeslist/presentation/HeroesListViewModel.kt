package com.filipibrentegani.marvelheroes.heroeslist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.domain.HeroesListUseCase
import com.filipibrentegani.marvelheroes.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HeroesListViewModel(
    private val useCase: HeroesListUseCase,
    private val pagingSource: HeroesPagingSource
) : BaseViewModel() {

    suspend fun getItems(): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20),
            pagingSourceFactory = {
                pagingSource
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun changeFavoriteState(hero: Hero) {
        viewModelScope.launch(scopes.ioScope) {
            useCase.changeFavoriteState(hero)
        }
    }
}