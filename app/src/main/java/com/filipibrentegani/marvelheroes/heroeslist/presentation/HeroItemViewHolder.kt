package com.filipibrentegani.marvelheroes.heroeslist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.databinding.LayoutHeroItemBinding
import com.filipibrentegani.marvelheroes.entity.domain.Hero
import com.filipibrentegani.marvelheroes.utils.setTextOrGone
import com.squareup.picasso.Picasso

class HeroItemViewHolder(
    private val binding: LayoutHeroItemBinding,
    private val favoriteCallback: ((Int, Hero) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(hero: Hero, position: Int) {
        binding.tvName.text = hero.name
        binding.tvDescription.setTextOrGone(hero.description)
        binding.btnFavorite.setImageResource(if (hero.favorite) R.drawable.favorite_checked else R.drawable.favorite_unchecked)
        binding.btnFavorite.setOnClickListener {
            hero.favorite = !hero.favorite
            favoriteCallback?.invoke(position, hero)
        }
        binding.ivThumbnail.background = null
        if (hero.thumbnail.isNotEmpty()) {
            Picasso.get().load(hero.thumbnail).into(binding.ivThumbnail)
        }
        binding.tvComics.text = hero.comics.available.toString()
        binding.tvSeries.text = hero.series.available.toString()
        binding.tvStories.text = hero.stories.available.toString()
        binding.tvEvents.text = hero.events.available.toString()
    }

    companion object {
        fun creteNewHolder(
            parent: ViewGroup,
            favoriteCallback: ((Int, Hero) -> Unit)?
        ): HeroItemViewHolder {
            val view =
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.layout_hero_item, parent, false)
            val binding = LayoutHeroItemBinding.bind(view)
            return HeroItemViewHolder(binding, favoriteCallback)
        }
    }
}