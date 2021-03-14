package com.filipibrentegani.marvelheroes.entity.data

import com.google.gson.annotations.SerializedName

data class ComicResponse(
    val available: Int,
    val collectionURI: String,
    @SerializedName("items")
    val comics: List<ComicItemResponse>
)