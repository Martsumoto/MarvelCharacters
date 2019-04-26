package com.marcelokmats.marvelcharacters.model

data class Response(
    val code: Int,
    val status: String,
    val data: Data
)

data class Data(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<MarvelCharacter>
)