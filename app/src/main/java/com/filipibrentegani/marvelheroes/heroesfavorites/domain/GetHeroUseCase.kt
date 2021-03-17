package com.filipibrentegani.marvelheroes.heroesfavorites.domain

import com.filipibrentegani.marvelheroes.entity.data.ComicResponse
import com.filipibrentegani.marvelheroes.entity.domain.Comic
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.entity.domain.Story
import com.filipibrentegani.marvelheroes.heroeslist.domain.IFavoriteHeroesRepository
import com.filipibrentegani.marvelheroes.heroeslist.domain.IHeroesListRepository
import com.filipibrentegani.marvelheroes.network.IConnectionUtils
import com.filipibrentegani.marvelheroes.network.ResultWrapper

class GetHeroUseCase(
    private val repository: IHeroesListRepository,
    private val repositoryFavorites: IFavoriteHeroesRepository,
    private val connectionUtils: IConnectionUtils
) :
    IGetHeroUseCase {
    override suspend fun getFavoriteHero(heroId: Int): ResultWrapper<Hero, Throwable> {
        try {
            if (!connectionUtils.isConnected()) {
                throw Exception("Não existe conectividade com a internet! Favor verificar a conexão e tentar novamente.")
            }
            val favorites = repositoryFavorites.getFavoriteHeroes()
            val characterResponse = repository.getHero(heroId)
            val path = characterResponse.thumbnail.path.replace("http", "https")
            val favorite = favorites.firstOrNull { favorite ->
                favorite.id == characterResponse.id
            }
            return ResultWrapper.Success(
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
            )
        } catch (ex: Exception) {
            return ResultWrapper.Error(ex)
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