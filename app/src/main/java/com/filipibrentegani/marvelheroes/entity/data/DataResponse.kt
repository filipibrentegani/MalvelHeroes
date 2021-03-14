package com.filipibrentegani.marvelheroes.entity.data

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("offset")
    val offSet: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterResponse>
)