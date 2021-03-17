package com.filipibrentegani.marvelheroes.heroesdetails.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipibrentegani.marvelheroes.databinding.ActivityHeroDetailsBinding
import com.filipibrentegani.marvelheroes.utils.setVisible
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroDetailsBinding
    private val adapterComics = ComicsAdapter()
    private val adapterSeries = ComicsAdapter()
    private val adapterStories = ComicsAdapter()
    private val adapterEvents = ComicsAdapter()
    private val viewModel: HeroDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.rvComics.layoutManager = LinearLayoutManager(this)
        binding.rvComics.adapter = adapterComics

        binding.rvSeries.layoutManager = LinearLayoutManager(this)
        binding.rvSeries.adapter = adapterSeries

        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.adapter = adapterStories

        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapterEvents

        loadingHeroDetails(intent.getIntExtra(HERO, 0))
        binding.btnTryAgain.setOnClickListener {
            loadingHeroDetails(intent.getIntExtra(HERO, 0))
        }
        binding.fab.setOnClickListener {
            viewModel.changeFavoriteState()
        }

        viewModel.heroNameLiveData.observe(this, Observer {
            title = it
        })
        viewModel.thumbnailLiveData.observe(this, Observer {
            Picasso.get().load(it).into(binding.ivThumbnail)
        })
        viewModel.descriptionLiveData.observe(this, Observer {
            binding.tvDescription.text = it
        })
        viewModel.comicsLiveData.observe(this, Observer {
            adapterComics.setItems(it)
        })
        viewModel.seriesLiveData.observe(this, Observer {
            adapterSeries.setItems(it)
        })
        viewModel.storiesLiveData.observe(this, Observer {
            adapterStories.setItems(it)
        })
        viewModel.eventsLiveData.observe(this, Observer {
            adapterEvents.setItems(it)
        })
        viewModel.favoriteIconLiveData.observe(this, Observer {
            binding.fab.setImageResource(it)
        })
        viewModel.favoriteIconContentDescriptionLiveData.observe(this, Observer {
            binding.fab.contentDescription = getString(it)
        })
        viewModel.showLoadingLiveData.observe(this, Observer {
            binding.progressBar.setVisible(it)
        })
        viewModel.showErrorLiveData.observe(this, Observer {
            binding.tvError.text = it.second
            binding.tvError.setVisible(it.first)
            binding.btnTryAgain.setVisible(it.first)
        })
        viewModel.showDataLiveData.observe(this, Observer {
            binding.gData.setVisible(it)
        })
    }

    private fun loadingHeroDetails(heroId: Int) {
        viewModel.setHero(heroId)
    }

    override fun onSupportNavigateUp(): Boolean {
        val returnIntent = Intent()
        setResult(viewModel.activityResult(), returnIntent)
        finish()
        return true
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        setResult(viewModel.activityResult(), returnIntent)
        super.onBackPressed()
    }

    companion object {
        private const val HERO = "HERO_ID"
        const val REQUEST_CODE = 2

        fun launchIntent(context: Context, heroID: Int): Intent {
            val intent = Intent(context, HeroDetailsActivity::class.java)
            intent.putExtra(HERO, heroID)
            return intent
        }
    }
}