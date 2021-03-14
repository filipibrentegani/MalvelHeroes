package com.filipibrentegani.marvelheroes.entity.data

data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailResponse,
    val resourceURI: String,
    val comics: ComicResponse,
    val series: ComicResponse,
    val stories: ComicResponse,
    val events: ComicResponse,
    val urls: List<URLResponse>
)