package com.marcelokmats.marvelcharacters.characterDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.marcelokmats.marvelcharacters.R
import com.marcelokmats.marvelcharacters.charactersList.CharactersListActivity
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import com.marcelokmats.marvelcharacters.util.setUrl
import kotlinx.android.synthetic.main.character_detail_activity.*

class CharacterDetailActivity : AppCompatActivity() {

    lateinit var mMarvelCharacter: MarvelCharacter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_detail_activity)

        mMarvelCharacter = intent.getParcelableExtra(CharactersListActivity.MARVEL_CHARACTER)
        this.populateViews()
    }

    private fun populateViews() {
        ivPhoto.setUrl(baseContext, mMarvelCharacter.thumbnail)
        tvDescription.text = mMarvelCharacter.description

        this.setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (collapsingToolbar as CollapsingToolbarLayout).title = mMarvelCharacter.name
    }
}