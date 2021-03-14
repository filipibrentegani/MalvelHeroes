package com.filipibrentegani.marvelheroes.entity.domain

data class Hero(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: String = "",
        var favorite: Boolean = false,
        var comics: Comic,
        var series: Comic,
        var stories: Comic,
        var events: Comic
)

data class Comic(
        val available: Int,
        val collectionUri: String,
        val heroId: Int,
        var items: List<Story>
)

data class Story(
        val resourceURI: String,
        val collectionUri: String,
        val name: String,
        val type: String
)