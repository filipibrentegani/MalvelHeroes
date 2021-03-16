package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.domain.IFavoriteHeroesRepository

class GetFavoriteHeroesUseCase(private val repositoryFavorites: IFavoriteHeroesRepository) :
    IGetFavoriteHeroesUseCase {

    override suspend fun getFavoriteHeroes(): List<Hero> {
        return repositoryFavorites.getFavoriteHeroes()
    }
}