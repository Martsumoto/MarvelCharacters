package com.marcelokmats.marvelcharacters

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.marcelokmats.marvelcharacters.api.MarvelApi
import com.marcelokmats.marvelcharacters.di.BaseViewModel
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import com.marcelokmats.marvelcharacters.util.getMarvelApiHash
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var marvelApi: MarvelApi

    private val subs = CompositeDisposable()

    val mCharactersReponseLiveData = MutableLiveData<List<MarvelCharacter>>()

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        subs.add(
            marvelApi.getAllCharacters("1",
                getApplication<Application>().resources.getString(R.string.marvel_public_key),
                getMarvelApiHash(getApplication<Application>().resources))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        mCharactersReponseLiveData.value = response?.data?.results ?: emptyList()
                    },
                    { Timber.e(it) }
                )
        )
    }
}
