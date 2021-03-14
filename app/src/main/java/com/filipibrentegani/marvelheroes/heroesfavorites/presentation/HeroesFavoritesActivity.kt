package com.filipibrentegani.marvelheroes.heroesfavorites.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipibrentegani.marvelheroes.databinding.ActivityHeroesFavoritesBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroesFavoritesActivity : AppCompatActivity() {

    lateinit var binding: ActivityHeroesFavoritesBinding
    val adapter: HeroesFavoritesAdapter by inject()
    val viewModel by viewModel<HeroesFavoritesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroesFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.setFavoriteCallback {
            viewModel.removeFavoriteHero(it)
        }

        viewModel.heroesLiveData.observe(this, Observer {
            adapter.setHeroes(it)
        })

        viewModel.showFavoriteHeroes()
    }

    companion object {
        fun launchIntent(context: Context): Intent {
            return Intent(context, HeroesFavoritesActivity::class.java)
        }
    }
}