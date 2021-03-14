package com.filipibrentegani.marvelheroes.localpersistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.filipibrentegani.marvelheroes.entity.domain.HeroRoom

@Database(entities = [HeroRoom::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun heroDao(): HeroDao
}