package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero

interface IGetFavoriteHeroesUseCase {
    suspend fun getFavoriteHeroes(): List<Hero>
}