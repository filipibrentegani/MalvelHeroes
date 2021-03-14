package com.filipibrentegani.marvelheroes.localpersistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.entity.domain.HeroRoom

@Dao
interface HeroDao {
    @Query("select * from heroroom")
    fun getAll(): List<HeroRoom>

    @Query("delete from heroroom where id = :id")
    fun removeFavorite(id: Int)

    @Insert
    fun insertFavorite(hero: HeroRoom)
}