package com.example.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        moviesAdapter = MoviesAdapter()
        recyclerView.adapter = moviesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.movies.observe(this, object : Observer<List<Movie>>{
            override fun onChanged(value: List<Movie>) {
                moviesAdapter.updateMovies(value)
            }
        })
        viewModel.isLoading.observe(this, object  : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                if (value) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })

        moviesAdapter.onReachEndListener = object : MoviesAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies()
            }
        }
        moviesAdapter.onMovieClickListener = object : MoviesAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent: Intent = MovieDetailActivity().newIntent(this@MainActivity, movie)
                startActivity(intent)
            }
        }
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewMovies)
        progressBar = findViewById(R.id.progressBar)
    }
}