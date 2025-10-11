package com.example.movies

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //    Первый токен!
    //    @GET("movie?token=42DXPYH-NWD42R7-GATZG3Q-RY559WD&field=rating.kp&search=5-70&sortField=votes.kp&sortType=-1&limit=10")
    //    @GET("movie?token=42DXPYH-NWD42R7-GATZG3Q-RY559WD&&limit=30")

    //    Второй токен
    @GET("movie?token=1N0C0RF-8JF48H8-NHK5T0S-Z3GGF7H&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=40")
    fun loadMovies(
        @retrofit2.http.Query("page") page: Int
    ): Single<MovieResponse>

    @GET("movie/{id}?token=1N0C0RF-8JF48H8-NHK5T0S-Z3GGF7H")
    fun loadTrailers(
        @Path("id") id: Int
    ): Single<TrailerResponse>
}