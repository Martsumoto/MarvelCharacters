package com.marcelokmats.marvelcharacters.charactersList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.marcelokmats.marvelcharacters.api.MarvelApi
import com.marcelokmats.marvelcharacters.datasource.CharacterDataSource
import com.marcelokmats.marvelcharacters.datasource.CharacterDataSourceFactory
import com.marcelokmats.marvelcharacters.di.BaseViewModel
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import com.marcelokmats.marvelcharacters.util.State
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CharactersListViewModel(application: Application) : BaseViewModel(application) {

    companion object {
        const val PAGE_SIZE = 10
    }

    @Inject
    lateinit var marvelApi: MarvelApi

    private val subs = CompositeDisposable()

    private val characterDataSourceFactory : CharacterDataSourceFactory
    var characterList : LiveData<PagedList<MarvelCharacter>>

    init {
        characterDataSourceFactory =
                CharacterDataSourceFactory(marvelApi, subs)
        val characterConfig = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
        characterList = LivePagedListBuilder<Int, MarvelCharacter>(characterDataSourceFactory, characterConfig).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<CharacterDataSource,
            State>(characterDataSourceFactory.characterDataSourceLiveData, CharacterDataSource::state)

    fun retry() {
        characterDataSourceFactory.characterDataSourceLiveData.value?.retry()
    }
}
