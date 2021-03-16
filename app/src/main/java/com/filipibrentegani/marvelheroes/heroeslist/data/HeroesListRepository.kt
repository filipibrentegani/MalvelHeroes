package com.filipibrentegani.marvelheroes.heroeslist.data

import com.filipibrentegani.marvelheroes.BuildConfig
import com.filipibrentegani.marvelheroes.entity.data.CharacterResponse
import com.filipibrentegani.marvelheroes.heroeslist.domain.IHeroesListRepository
import com.filipibrentegani.marvelheroes.network.MarvelApi
import com.filipibrentegani.marvelheroes.network.NetworkUtils

class HeroesListRepository(private val api: MarvelApi) : IHeroesListRepository {
    override suspend fun getHeroes(loadSize: Int, position: Int, criteria: String): List<CharacterResponse> {
        val timestamp = System.currentTimeMillis()
        return api.getCharacters(
                criteria,
                BuildConfig.PUBLIC_KEY_MARVEL_API,
                timestamp,
                NetworkUtils.marvelHashCode(timestamp),
                loadSize,
                position
            ).data.results
    }
}