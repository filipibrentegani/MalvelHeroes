package com.filipibrentegani.marvelheroes.heroeslist.domain

import com.filipibrentegani.marvelheroes.entity.data.ComicResponse
import com.filipibrentegani.marvelheroes.entity.domain.Comic
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.entity.domain.Story

class GetHeroesUseCase(
    private val repositoryList: IHeroesListRepository,
    private val repositoryFavorites: IFavoriteHeroesRepository
) : IGetHeroesUseCase {
    override suspend fun getHeroes(
        loadSize: Int,
        position: Int,
        criteria: String
    ): List<Hero> {
        val favorites = repositoryFavorites.getFavoriteHeroes()
        return repositoryList.getHeroes(loadSize, position, criteria).map { characterResponse ->
                val favorite = favorites.firstOrNull { favorite ->
                    favorite.id == characterResponse.id
                }
                val path = characterResponse.thumbnail.path.replace("http", "https")
                Hero(
                    characterResponse.id,
                    characterResponse.name,
                    characterResponse.description,
                    "$path.${characterResponse.thumbnail.extension}",
                    favorite?.favorite ?: false,
                    convertComic(characterResponse.comics, characterResponse.id),
                    convertComic(characterResponse.series, characterResponse.id),
                    convertComic(characterResponse.stories, characterResponse.id),
                    convertComic(characterResponse.events, characterResponse.id)
                )
            }
    }

    private fun convertComic(comic: ComicResponse, heroId: Int): Comic {
        return Comic(
            comic.available,
            comic.collectionURI,
            heroId,
            comic.comics.map { comicResponse ->
                Story(
                    comicResponse.resourceURI,
                    comic.collectionURI,
                    comicResponse.name,
                    comicResponse.type ?: ""
                )
            })
    }
}