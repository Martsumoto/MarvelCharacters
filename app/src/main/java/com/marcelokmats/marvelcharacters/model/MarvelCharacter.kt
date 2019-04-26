package com.marcelokmats.marvelcharacters.model

data class MarvelCharacter (
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)