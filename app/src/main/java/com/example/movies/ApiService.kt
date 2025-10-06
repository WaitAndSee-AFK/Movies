package com.example.movies

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

fun interface ApiService {

    @GET("movie?token=42DXPYH-NWD42R7-GATZG3Q-RY559WD&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&page=2&limit=5")
    fun loadMovies(): Single<MovieResponse>
}