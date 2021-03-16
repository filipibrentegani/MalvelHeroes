package com.filipibrentegani.marvelheroes.heroeslist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.domain.IChangeFavoriteStateUseCase
import com.filipibrentegani.marvelheroes.heroeslist.domain.IGetHeroesUseCase
import com.filipibrentegani.marvelheroes.network.IConnectionUtils
import com.filipibrentegani.marvelheroes.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HeroesListViewModel(
    private val useCaseGetHeroes: IGetHeroesUseCase,
    private val useCaseChangeFavoriteStateUseCase: IChangeFavoriteStateUseCase,
    private val connectionUtils: IConnectionUtils
) : BaseViewModel() {

    private val showList = MutableLiveData<Boolean>()
    val showListLiveData: LiveData<Boolean> = showList
    private val showEmptyState = MutableLiveData<Boolean>()
    val showEmptyStateLiveData: LiveData<Boolean> = showEmptyState
    private val showLoading = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean> = showLoading
    private val showError = MutableLiveData<Pair<Boolean, String>>()
    val showErrorLiveData: LiveData<Pair<Boolean, String>> = showError

    suspend fun getItems(criteria: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20),
            pagingSourceFactory = {
                HeroesPagingSource(
                    useCaseGetHeroes,
                    criteria,
                    connectionUtils
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun showListState(loadState: CombinedLoadStates, itemLoadedCount: Int) {
        showLoading.value = false
        showList.value = false
        showEmptyState.value = false
        showError.value = Pair(false, "")
        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && itemLoadedCount < 1) {
            showEmptyState.value = true
        } else if (loadState.source.refresh is LoadState.Loading) {
            showLoading.value = true
        } else if (loadState.source.refresh is LoadState.Error) {
            showError.value =
                Pair(true, (loadState.source.refresh as LoadState.Error).error.message ?: "Error")
        } else {
            showList.value = true
        }
    }

    fun changeFavoriteState(hero: Hero) {
        viewModelScope.launch(scopes.ioScope) {
            useCaseChangeFavoriteStateUseCase.changeFavoriteState(hero)
        }
    }
}