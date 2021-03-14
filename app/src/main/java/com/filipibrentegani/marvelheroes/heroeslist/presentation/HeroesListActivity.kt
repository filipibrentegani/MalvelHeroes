package com.filipibrentegani.marvelheroes.heroeslist.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.databinding.ActivityHeroesListBinding
import com.filipibrentegani.marvelheroes.heroesfavorites.presentation.HeroesFavoritesActivity
import com.filipibrentegani.marvelheroes.utils.setVisible
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroesListBinding
    private val adapter: HeroesListAdapter by inject()
    private val viewModel by viewModel<HeroesListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = HeroesListLoadAdapter { adapter.retry() },
            footer = HeroesListLoadAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            binding.recyclerView.setVisible(loadState.source.refresh is LoadState.NotLoading)
            binding.progressbar.setVisible(loadState.source.refresh is LoadState.Loading)
            binding.tvError.setVisible(loadState.source.refresh is LoadState.Error)
        }
        adapter.setFavoriteCallback {
            viewModel.changeFavoriteState(it)
        }

        lifecycleScope.launch {
            viewModel.getItems().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(HeroesFavoritesActivity.launchIntent(context = this))
                false
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}