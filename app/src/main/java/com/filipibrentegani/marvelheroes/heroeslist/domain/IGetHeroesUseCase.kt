package com.filipibrentegani.marvelheroes.heroeslist.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero

interface IGetHeroesUseCase {
    suspend fun getHeroes(loadSize: Int, position: Int, criteria: String): List<Hero>
}