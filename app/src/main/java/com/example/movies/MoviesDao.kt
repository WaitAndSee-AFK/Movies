package com.example.movies

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable

@Dao
interface MoviesDao {

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM favourite_movies WHERE id =:id")
    fun getFavouriteMovie(id: Int): LiveData<Movie>

    @Insert
    fun insertMovie(movie: Movie): Completable

    @Query("DELETE FROM favourite_movies WHERE id =:id")
    fun removeMovie(id: Int): Completable
}