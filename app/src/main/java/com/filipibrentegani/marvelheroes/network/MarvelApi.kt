package com.filipibrentegani.marvelheroes.network

import com.filipibrentegani.marvelheroes.entity.data.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") criteria: String,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: Long,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") page: Int
    ): CharactersResponse

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") heroId: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: Long,
        @Query("hash") hash: String
    ): CharactersResponse
}