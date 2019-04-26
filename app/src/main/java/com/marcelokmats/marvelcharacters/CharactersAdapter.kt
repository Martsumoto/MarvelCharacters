package com.marcelokmats.marvelcharacters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharactersAdapter(
    private val context: Context,
    private val marvelCharacterList: List<MarvelCharacter>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.character_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = marvelCharacterList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(marvelCharacterList[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: MarvelCharacter) = with(itemView) {
            name.text = character.name
        }
    }
}