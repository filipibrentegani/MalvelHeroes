package com.filipibrentegani.marvelheroes.heroeslist.data

import com.filipibrentegani.marvelheroes.entity.domain.Comic
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.entity.domain.HeroRoom
import com.filipibrentegani.marvelheroes.heroeslist.domain.IFavoriteHeroesRepository
import com.filipibrentegani.marvelheroes.localpersistence.HeroDao
import com.google.gson.Gson

class FavoriteHeroesRepository(private val localPersistence: HeroDao) :
    IFavoriteHeroesRepository {

    private var updatedListHero = ArrayList<Hero>()

    override suspend fun getFavoriteHeroes(): List<Hero> {
        if (invalidatedCache) {
            updateCachedValue()
        }
        return updatedListHero
    }

    override suspend fun addFavoriteHeroes(hero: Hero) {
        val heroRoom = HeroRoom(
            hero.id,
            hero.name,
            hero.description,
            hero.thumbnail,
            hero.favorite,
            Gson().toJson(hero.comics),
            Gson().toJson(hero.series),
            Gson().toJson(hero.stories),
            Gson().toJson(hero.events)
        )
        localPersistence.insertFavorite(heroRoom)
        invalidatedCache = true
    }

    override suspend fun removeFavoriteHero(hero: Hero) {
        localPersistence.removeFavorite(hero.id)
        invalidatedCache = true
    }

    private fun updateCachedValue() {
        updatedListHero.clear()
        val list = localPersistence.getAll().map {
            Hero(
                it.id,
                it.name,
                it.description,
                it.thumbnail,
                it.favorite,
                Gson().fromJson(it.comics, Comic::class.java),
                Gson().fromJson(it.series, Comic::class.java),
                Gson().fromJson(it.stories, Comic::class.java),
                Gson().fromJson(it.events, Comic::class.java)
            )
        }
        updatedListHero.addAll(list)
        invalidatedCache = false
    }

    companion object {
        private var invalidatedCache = true
    }
}