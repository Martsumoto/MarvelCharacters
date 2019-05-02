package com.marcelokmats.marvelcharacters.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.marcelokmats.marvelcharacters.api.MarvelApi
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import io.reactivex.disposables.CompositeDisposable

class CharacterDataSourceFactory(private val marvelApi: MarvelApi,
                                 private val compositeDisposable: CompositeDisposable

) : DataSource.Factory<Int, MarvelCharacter>() {

    val characterDataSourceLiveData = MutableLiveData<CharacterDataSource>()

    override fun create(): DataSource<Int, MarvelCharacter> {
        val characterDataSource =
                CharacterDataSource(marvelApi, compositeDisposable)
        characterDataSourceLiveData.postValue(characterDataSource)

        return characterDataSource
    }

}