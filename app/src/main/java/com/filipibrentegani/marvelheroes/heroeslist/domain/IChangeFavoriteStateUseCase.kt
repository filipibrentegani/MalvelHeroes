package com.filipibrentegani.marvelheroes.heroeslist.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero

interface IChangeFavoriteStateUseCase {
    suspend fun changeFavoriteState(hero: Hero)
}