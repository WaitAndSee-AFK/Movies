package com.example.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouriteMoviesActivity : AppCompatActivity() {
    private lateinit var recyclerViewFavouriteMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var viewModel: FavouriteMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favourite_movie)
        initViews()

        moviesAdapter = MoviesAdapter()
        recyclerViewFavouriteMovies.layoutManager = GridLayoutManager(this, 2)
        recyclerViewFavouriteMovies.adapter = moviesAdapter
        moviesAdapter.onMovieClickListener = object : MoviesAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = MovieDetailActivity().newIntent(this@FavouriteMoviesActivity, movie)
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this).get(FavouriteMoviesViewModel::class.java)
        viewModel.getMovies().observe(this, object : androidx.lifecycle.Observer<List<Movie>> {
            override fun onChanged(value: List<Movie>) {
                moviesAdapter.updateMovies(value)
            }
        })
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, FavouriteMoviesActivity::class.java)

    }

    private fun initViews() {
        recyclerViewFavouriteMovies = findViewById(R.id.recyclerViewFavouriteMovies)
    }
}