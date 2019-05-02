package com.marcelokmats.marvelcharacters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.marcelokmats.marvelcharacters.model.MarvelCharacter
import com.marcelokmats.marvelcharacters.util.State

class CharactersAdapter(
    private val characterSelectedListener: (MarvelCharacter) -> Unit,
    private val retryCallback: () -> Unit
): PagedListAdapter<MarvelCharacter, RecyclerView.ViewHolder>(diffCallback) {

    private var state = State.LOADING

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MarvelCharacter>() {
            override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.character_list_item -> CharacterViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else ->  throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = state != State.DONE

    override fun getItemViewType(position: Int): Int =
        if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.character_list_item
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            R.layout.character_list_item -> (holder as CharacterViewHolder).bind(getItem(position), characterSelectedListener)
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bind(state)
        }
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}