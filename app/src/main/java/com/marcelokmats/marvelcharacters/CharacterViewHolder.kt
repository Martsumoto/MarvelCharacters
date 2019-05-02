package com.marcelokmats.marvelcharacters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(character: MarvelCharacter?,
             characterSelectedListener: (MarvelCharacter) -> Unit) = with(itemView) {
        character?.let {
            name.text = character.name
        }
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.character_list_item, parent, false)
            return CharacterViewHolder(view)
        }
    }
}