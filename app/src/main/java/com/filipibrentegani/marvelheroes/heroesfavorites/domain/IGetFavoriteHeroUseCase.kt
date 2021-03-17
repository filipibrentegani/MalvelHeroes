package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero

interface IGetFavoriteHeroUseCase {
    suspend fun getFavoriteHero(heroId: Int): Hero?
}