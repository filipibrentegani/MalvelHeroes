package com.filipibrentegani.marvelheroes.heroeslist.domain

import com.filipibrentegani.marvelheroes.entity.data.CharacterResponse

interface IHeroesListRepository {
    suspend fun getHeroes(loadSize: Int, position: Int, criteria: String): List<CharacterResponse>
}