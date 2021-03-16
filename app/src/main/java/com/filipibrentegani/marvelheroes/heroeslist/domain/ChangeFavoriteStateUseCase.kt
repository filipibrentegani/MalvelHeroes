package com.filipibrentegani.marvelheroes.heroeslist.domain

import com.filipibrentegani.marvelheroes.entity.domain.Hero

class ChangeFavoriteStateUseCase(private val repositoryFavorites: IFavoriteHeroesRepository) :
    IChangeFavoriteStateUseCase {

    override suspend fun changeFavoriteState(hero: Hero) {
        if (hero.favorite) {
            repositoryFavorites.addFavoriteHeroes(hero)
        } else {
            repositoryFavorites.removeFavoriteHero(hero)
        }
    }
}