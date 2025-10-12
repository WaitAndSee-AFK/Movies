package com.example.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesDao = MovieDatabase.getInstance(application).moviesDao()

    fun getMovies(): LiveData<List<Movie>> {
        return moviesDao.getAllFavoriteMovies()
    }
}