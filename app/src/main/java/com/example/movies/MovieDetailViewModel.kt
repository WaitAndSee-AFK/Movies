package com.example.movies

import android.app.Application
import android.util.Log
import androidx.core.util.Function
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "MovieDetailViewModel"

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val moviesDao: MoviesDao = MovieDatabase.getInstance(getApplication()).moviesDao()
    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>>
        get() = _reviews

    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>>
        get() = _trailers

    private val compositeDisposable = CompositeDisposable()

    fun removeMovie(id: Int) {
        val disposable = moviesDao.removeMovie(id)
            .subscribeOn(Schedulers.io())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun insertMovie(movie: Movie) {
        val disposable = moviesDao.insertMovie(movie)
            .subscribeOn(Schedulers.io())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun getFavouriteMovie(id: Int): LiveData<Movie> {
        return moviesDao.getFavouriteMovie(id)
    }

    fun loadReviews(id: Int) {
        val disposable = ApiFactory.apiService.loadReviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.reviews }
            .subscribe({ reviewsList ->
                _reviews.value = reviewsList
            }, { err ->
                Log.d(TAG, err.toString())
            })
        compositeDisposable.add(disposable)
    }

    fun loadTrailers(id: Int) {
        val disposable = ApiFactory.apiService.loadTrailers(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response -> response.trailersList.trailers }
            .subscribe({ trailerList ->
                _trailers.value = trailerList
            }, { err ->
                Log.d(TAG, err.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}