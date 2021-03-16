package com.filipibrentegani.marvelheroes.heroeslist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.databinding.LayoutHeroFooterBinding
import com.filipibrentegani.marvelheroes.utils.setVisible

class HeroFooterViewHolder(
    private val binding: LayoutHeroFooterBinding,
    private val retry: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.progressBar.setVisible(loadState is LoadState.Loading)
        binding.btnTryAgain.setVisible(loadState is LoadState.Error)
        binding.tvError.setVisible(loadState is LoadState.Error)
        if (loadState is LoadState.Error) {
            binding.tvError.text = loadState.error.message
        }
        binding.btnTryAgain.setOnClickListener {
            retry.invoke()
        }
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