package com.marcelokmats.marvelcharacters.api

import com.marcelokmats.marvelcharacters.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    companion object {
        const val URL = "http://gateway.marvel.com/v1/public/"
    }

    @GET("characters")
    fun getAllCharacters(
    @Query("ts") timestamp : String,
    @Query("apikey") apikey : String,
    @Query("hash") hash : String,
    @Query("limit") limit : Int = 10
    ) : Observable<Response>
}