package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.network.ResultWrapper

interface IGetHeroUseCase {
    suspend fun getFavoriteHero(heroId: Int): ResultWrapper<Hero, Throwable>
}