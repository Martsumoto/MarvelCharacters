package com.marcelokmats.marvelcharacters.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarvelCharacter(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
) : Parcelable