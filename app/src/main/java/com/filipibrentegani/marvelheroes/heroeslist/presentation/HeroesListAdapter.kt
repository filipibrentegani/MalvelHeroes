package com.filipibrentegani.marvelheroes.heroeslist.presentation

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.filipibrentegani.marvelheroes.entity.domain.Hero

class HeroesListAdapter: PagingDataAdapter<Hero, HeroItemViewHolder>(DiffUtilCallBack()) {

    private var favoriteCallback: ((Hero) -> Unit)? = null

    fun setFavoriteCallback(callback: (Hero) -> Unit) {
        favoriteCallback = callback
    }

    override fun onBindViewHolder(holder: HeroItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroItemViewHolder {
        return HeroItemViewHolder.creteNewHolder(parent) { position, hero ->
            favoriteCallback?.invoke(hero)
            notifyItemChanged(position)
        }
    }
}

class HeroesListLoadAdapter(private val retry: () -> Unit) : LoadStateAdapter<HeroFooterViewHolder>() {
    override fun onBindViewHolder(holder: HeroFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): HeroFooterViewHolder {
        return HeroFooterViewHolder.creteNewHolder(parent, retry)
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<Hero>() {
    override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
        return oldItem == newItem
    }
}