package com.filipibrentegani.marvelheroes.heroeslist.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.domain.IGetHeroesUseCase
import com.filipibrentegani.marvelheroes.network.IConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HeroesPagingSource(
    private val useCase: IGetHeroesUseCase,
    private val criteria: String,
    private val connectionUtils: IConnectionUtils
) : PagingSource<Int, Hero>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        val position = params.key ?: 1
        return try {
            var heroes: List<Hero>
            withContext(Dispatchers.IO) {
                if (!connectionUtils.isConnected()) {
                    throw Exception("Não existe conectividade com a internet! Favor verificar a conexão e tentar novamente.")
                }
                heroes = useCase.getHeroes(params.loadSize, position, criteria)
            }
            val nextKey = if (heroes.isEmpty()) {
                null
            } else {
                position + heroes.size
            }
            LoadResult.Page(
                heroes,
                if (position == 1) null else position - 1,
                if (nextKey == position) null else nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition
    }
}