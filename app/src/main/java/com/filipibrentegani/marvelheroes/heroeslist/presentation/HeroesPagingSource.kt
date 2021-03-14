package com.filipibrentegani.marvelheroes.heroeslist.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.domain.HeroesListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class HeroesPagingSource(private val useCase: HeroesListUseCase) : PagingSource<Int, Hero>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        val position = params.key ?: 1
        return try {
            var heroes: List<Hero> = listOf()
            withContext(Dispatchers.IO) {
                heroes = useCase.getHeroes(params.loadSize, position)
            }
            val nextKey = if (heroes.isEmpty()) {
                null
            } else {
                position + heroes.size
            }
            LoadResult.Page(heroes, if (position == 1) null else position - 1, if (nextKey == position) null else nextKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition
    }
}