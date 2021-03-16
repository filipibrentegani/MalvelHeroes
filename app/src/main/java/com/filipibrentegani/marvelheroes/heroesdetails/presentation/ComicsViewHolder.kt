package com.filipibrentegani.marvelheroes.heroesdetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.databinding.LayoutComicItemBinding
import com.filipibrentegani.marvelheroes.entity.domain.Story

class ComicsViewHolder(private val binding: LayoutComicItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(story: Story) {
        binding.tvEvent.text = story.name
    }

    companion object {
        fun creteNewHolder(
            parent: ViewGroup
        ): ComicsViewHolder {
            val view =
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.layout_comic_item, parent, false)
            val binding = LayoutComicItemBinding.bind(view)
            return ComicsViewHolder(binding)
        }
    }
}