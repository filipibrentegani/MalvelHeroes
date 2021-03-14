package com.filipibrentegani.marvelheroes.heroeslist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.filipibrentegani.marvelheroes.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel by viewModel<HeroesListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heroes_list)
    }
}