package com.marcelokmats.marvelcharacters.charactersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelokmats.marvelcharacters.R
import com.marcelokmats.marvelcharacters.util.State
import kotlinx.android.synthetic.main.network_state_item.view.*

class NetworkStateItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        if (status == State.DONE) {
            itemView.btRetry.visibility = View.GONE
            itemView.progressBar.visibility = View.GONE
            itemView.tvErrorMessage.visibility = View.GONE
        } else {
            itemView.progressBar.visibility = if (status == State.LOADING) View.VISIBLE else View.GONE
            itemView.tvErrorMessage.visibility = if (status == State.ERROR) View.VISIBLE else View.GONE
            itemView.btRetry.visibility = if (status == State.ERROR) View.VISIBLE else View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_item, parent, false)
            view.btRetry.setOnClickListener { retry() }
            return NetworkStateItemViewHolder(view)
        }
    }
}