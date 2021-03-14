package com.filipibrentegani.marvelheroes.entity.domain

import androidx.room.*

@Entity
data class HeroRoom(
        @PrimaryKey
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: String = "",
        var favorite: Boolean = false,
        var comics: String,
        var series: String,
        var stories: String,
        var events: String
)