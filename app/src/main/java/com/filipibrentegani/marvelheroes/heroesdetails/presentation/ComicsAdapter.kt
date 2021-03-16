package com.filipibrentegani.marvelheroes.heroesdetails.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filipibrentegani.marvelheroes.entity.domain.Comic
import com.filipibrentegani.marvelheroes.entity.domain.Story

class ComicsAdapter: RecyclerView.Adapter<ComicsViewHolder>() {

    private val comics = arrayListOf<Story>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        return ComicsViewHolder.creteNewHolder(parent)
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.bind(comics[position])
    }

    fun setItems(comicsList: List<Story>) {
        comics.addAll(comicsList)
        notifyDataSetChanged()
    }
}