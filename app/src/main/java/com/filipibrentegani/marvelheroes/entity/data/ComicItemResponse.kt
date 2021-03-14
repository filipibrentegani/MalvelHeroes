package com.filipibrentegani.marvelheroes.entity.data

data class ComicItemResponse(
    val resourceURI: String,
    val name: String,
    val type: String? = null
)