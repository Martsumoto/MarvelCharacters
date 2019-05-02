package com.marcelokmats.marvelcharacters.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.marcelokmats.marvelcharacters.MainViewModel.Companion.PAGE_SIZE
import com.marcelokmats.marvelcharacters.api.MarvelApi
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import com.marcelokmats.marvelcharacters.util.State
import com.marcelokmats.marvelcharacters.util.getMarvelApiHash
import com.marcelokmats.marvelcharacters.util.getMarvelApiKey
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CharacterDataSource(private val marvelApi: MarvelApi,
                          private val compositeDisposable: CompositeDisposable
): PageKeyedDataSource<Int, MarvelCharacter>() {

    var state : MutableLiveData<State> = MutableLiveData()
    private var retryCompletable : Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MarvelCharacter>) {
        updateState(State.LOADING)

        val ts = System.currentTimeMillis().toString()

        compositeDisposable.add(
            marvelApi.getAllCharacters(ts, getMarvelApiKey(), getMarvelApiHash(ts), PAGE_SIZE, 0)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.data.results, null, 2)
                    },
                    {
                        Timber.e(it)
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MarvelCharacter>) {
        updateState(State.LOADING)

        val ts = System.currentTimeMillis().toString()

        compositeDisposable.add(
            marvelApi.getAllCharacters(ts, getMarvelApiKey(), getMarvelApiHash(ts), PAGE_SIZE, params.key * PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            response ->
                        updateState(State.DONE)
                        callback.onResult(response.data.results, params.key + 1)
                    },
                    {
                        Timber.e(it)
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MarvelCharacter>) {
    }


    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}