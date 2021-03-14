package com.filipibrentegani.marvelheroes.heroeslist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.databinding.LayoutHeroFooterBinding

class HeroFooterViewHolder(
    private val binding: LayoutHeroFooterBinding,
    retry: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.progressBar.visibility = View.VISIBLE
    }

    companion object {
        fun creteNewHolder(parent: ViewGroup, retry: () -> Unit): HeroFooterViewHolder {
            val view =
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.layout_hero_footer, parent, false)
            val binding = LayoutHeroFooterBinding.bind(view)
            return HeroFooterViewHolder(binding, retry)
        }
    }
}