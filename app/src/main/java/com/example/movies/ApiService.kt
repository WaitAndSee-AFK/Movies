package com.example.movies

import androidx.room.Query
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

fun interface ApiService {

    @GET("movie?token=42DXPYH-NWD42R7-GATZG3Q-RY559WD&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=1")
    fun loadMovies(
        @retrofit2.http.Query("page") page: Int
    ): Single<MovieResponse>
}