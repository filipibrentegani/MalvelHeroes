package com.filipibrentegani.marvelheroes.heroeslist.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipibrentegani.marvelheroes.R
import com.filipibrentegani.marvelheroes.databinding.ActivityHeroesListBinding
import com.filipibrentegani.marvelheroes.heroesdetails.presentation.HeroDetailsActivity
import com.filipibrentegani.marvelheroes.heroesfavorites.presentation.HeroesFavoritesActivity
import com.filipibrentegani.marvelheroes.utils.KeyboardUtils
import com.filipibrentegani.marvelheroes.utils.setVisible
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroesListBinding
    private val adapter: HeroesListAdapter by inject()
    private val viewModel by viewModel<HeroesListViewModel>()
    private var searchJob: Job? = null

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
            viewModel.showListState(loadState, adapter.itemCount)
        }

        adapter.setFavoriteCallback {
            viewModel.changeFavoriteState(it)
        }

        adapter.setShowHeroDetailsCallback {
            startActivityForResult(
                HeroDetailsActivity.launchIntent(binding.root.context, it.id),
                HeroDetailsActivity.REQUEST_CODE
            )
        }

        binding.btnTryAgain.setOnClickListener {
            search()
        }

        binding.etFind.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                search()
                true
            } else {
                false
            }
        }
        binding.ivFind.setOnClickListener {
            search()
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy {
                    it.refresh
                }
                .filter {
                    it.refresh is LoadState.NotLoading
                }
                .collect {
                    binding.recyclerView.scrollToPosition(0)
                }
        }

        viewModel.showEmptyStateLiveData.observe(this, Observer {
            binding.tvEmptyState.setVisible(it)
        })
        viewModel.showErrorLiveData.observe(this, Observer {
            binding.tvError.text = it.second
            binding.tvError.setVisible(it.first)
            binding.btnTryAgain.setVisible(it.first)
        })
        viewModel.showListLiveData.observe(this, Observer {
            binding.recyclerView.setVisible(it)
        })
        viewModel.showLoadingLiveData.observe(this, Observer {
            binding.progressbar.setVisible(it)
        })
    }

    private fun search() {
        binding.etFind.text?.trim()?.let {
            if (it.isNotEmpty()) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    viewModel.getItems(it.toString()).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
        }
        KeyboardUtils.hideKeyboard(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HeroDetailsActivity.REQUEST_CODE ||
            requestCode == HeroesFavoritesActivity.REQUEST_CODE
        ) {
            if (resultCode == Activity.RESULT_OK) {
                search()
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
                startActivityForResult(
                    HeroesFavoritesActivity.launchIntent(context = this),
                    HeroesFavoritesActivity.REQUEST_CODE
                )
                false
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}