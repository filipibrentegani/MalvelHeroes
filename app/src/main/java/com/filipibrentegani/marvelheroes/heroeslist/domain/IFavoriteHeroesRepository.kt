package com.filipibrentegani.marvelheroes.heroeslist.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero

interface IFavoriteHeroesRepository {
    suspend fun getFavoriteHeroes() : List<Hero>
    suspend fun addFavoriteHeroes(hero: Hero)
    suspend fun removeFavoriteHero(hero: Hero)
}