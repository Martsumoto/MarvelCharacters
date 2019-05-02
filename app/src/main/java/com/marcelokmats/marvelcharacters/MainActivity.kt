package com.marcelokmats.marvelcharacters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: CharactersAdapter

    private lateinit var mMainViewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        this.setupCharactersList()
    }

    override fun onResume() {
        super.onResume()
//        mMainViewModel.mCharactersReponseLiveData.observe(this, Observer {
//            characters -> populateCharactersList(characters)
//        })
    }
//
//    private fun populateCharactersList(characters: List<MarvelCharacter>) {
//        mAdapter = CharactersAdapter(this, characters)
//        list.adapter = mAdapter
//    }

    private fun setupCharactersList() {
        val adapter = CharactersAdapter (characterSelectedListener = {}, retryCallback = { mMainViewModel.retry() })
        list.adapter = adapter
        mMainViewModel.characterList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
