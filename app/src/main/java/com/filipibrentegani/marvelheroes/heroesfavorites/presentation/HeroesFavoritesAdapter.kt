package com.filipibrentegani.marvelheroes.heroesfavorites.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.heroeslist.presentation.HeroItemViewHolder

class HeroesFavoritesAdapter : RecyclerView.Adapter<HeroItemViewHolder>() {

    private val items = arrayListOf<Hero>()
    private var favoriteCallback: ((Hero) -> Unit)? = null

    fun setFavoriteCallback(callback: (Hero) -> Unit) {
        favoriteCallback = callback
    }

    fun setHeroes(heroes: List<Hero>) {
        items.addAll(heroes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroItemViewHolder {
        return HeroItemViewHolder.creteNewHolder(parent) { position, hero ->
            favoriteCallback?.invoke(hero)
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: HeroItemViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}