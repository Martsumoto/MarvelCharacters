package com.marcelokmats.marvelcharacters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marcelokmats.marvelcharacters.characterDetail.CharacterDetailActivity
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: CharactersAdapter

    private lateinit var mMainViewModel: MainViewModel

    companion object {
        const val MARVEL_CHARACTER = "MARVEL_CHARACTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        this.setupCharactersList()
    }

    private fun setupCharactersList() {
        val adapter = CharactersAdapter (characterSelectedListener = this::onCharacterClick, retryCallback = { mMainViewModel.retry() })
        list.adapter = adapter
        mMainViewModel.characterList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun onCharacterClick(marvelCharacter: MarvelCharacter) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra(MARVEL_CHARACTER, marvelCharacter)
        startActivity(intent)
    }

}
