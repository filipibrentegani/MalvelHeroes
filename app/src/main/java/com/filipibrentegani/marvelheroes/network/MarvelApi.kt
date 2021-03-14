package com.filipibrentegani.marvelheroes.network

import com.filipibrentegani.marvelheroes.entity.data.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: Long,
        @Query("hash") hash: String
    ) : CharactersResponse
}