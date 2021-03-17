package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.domain.IFavoriteHeroesRepository

class GetFavoriteHeroUseCase(private val repositoryFavorites: IFavoriteHeroesRepository) :
    IGetFavoriteHeroUseCase {
    override suspend fun getFavoriteHero(heroId: Int): Hero? {
        return repositoryFavorites.getFavoriteHero(heroId)
    }
}