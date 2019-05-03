package com.marcelokmats.marvelcharacters.charactersList

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marcelokmats.marvelcharacters.BuildConfig
import com.marcelokmats.marvelcharacters.R
import com.marcelokmats.marvelcharacters.characterDetail.CharacterDetailActivity
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class CharactersListActivity : AppCompatActivity() {

    private lateinit var mAdapter: CharactersAdapter

    private lateinit var mViewModel: CharactersListViewModel

    companion object {
        const val MARVEL_CHARACTER = "MARVEL_CHARACTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        mViewModel = ViewModelProviders.of(this).get(CharactersListViewModel::class.java)
        this.setupCharactersList()
    }

    private fun setupCharactersList() {
        val adapter =
            CharactersAdapter(characterSelectedListener = this::onCharacterClick,
                retryCallback = { mViewModel.retry() })
        list.adapter = adapter
        mViewModel.characterList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun onCharacterClick(marvelCharacter: MarvelCharacter) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra(MARVEL_CHARACTER, marvelCharacter)
        startActivity(intent)
    }

}
