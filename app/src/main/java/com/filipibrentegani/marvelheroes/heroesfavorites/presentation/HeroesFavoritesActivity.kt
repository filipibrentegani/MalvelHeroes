package com.filipibrentegani.marvelheroes.heroesfavorites.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipibrentegani.marvelheroes.databinding.ActivityHeroesFavoritesBinding
import com.filipibrentegani.marvelheroes.heroesdetails.presentation.HeroDetailsActivity
import com.filipibrentegani.marvelheroes.utils.setVisible
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.setFavoriteCallback {
            viewModel.removeFavoriteHero(it)
        }

        adapter.showHeroDetailsCallback {
            startActivityForResult(
                HeroDetailsActivity.launchIntent(binding.root.context, it),
                HeroDetailsActivity.REQUEST_CODE
            )
        }

        viewModel.heroesLiveData.observe(this, Observer {
            adapter.setHeroes(it)
        })

        viewModel.hasNoFavoritesLiveData.observe(this, Observer {
            binding.tvNoFavorites.visibility = it
        })

        viewModel.showLoadingLiveData.observe(this, Observer {
            binding.progressBar.setVisible(it)
        })

        viewModel.showFavoriteHeroes()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HeroDetailsActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.showFavoriteHeroes()
            }
        }
    }

    companion object {
        fun launchIntent(context: Context): Intent {
            return Intent(context, HeroesFavoritesActivity::class.java)
        }
    }
}