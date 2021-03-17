package com.filipibrentegani.marvelheroes.heroeslist.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero

class GetFavoriteHeroesUseCase(private val repositoryFavorites: IFavoriteHeroesRepository) :
    IGetFavoriteHeroesUseCase {

    override suspend fun getFavoriteHeroes(): List<Hero> {
        return repositoryFavorites.getFavoriteHeroes()
    }
}