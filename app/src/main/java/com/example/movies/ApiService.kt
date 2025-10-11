package com.example.movies

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //    Первый токен!
    //    @GET("movie?token=42DXPYH-NWD42R7-GATZG3Q-RY559WD&field=rating.kp&search=5-70&sortField=votes.kp&sortType=-1&limit=10")

    //    Второй токен!
    //    @GET("movie?token=1N0C0RF-8JF48H8-NHK5T0S-Z3GGF7H&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=40")

    //    Загрузка фильмов
    @GET("movie?token=1N0C0RF-8JF48H8-NHK5T0S-Z3GGF7H&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=40")
    fun loadMovies(
        @retrofit2.http.Query("page") page: Int
    ): Single<MovieResponse>

    //    Загрузка трейлеров
    @GET("movie/{id}?token=1N0C0RF-8JF48H8-NHK5T0S-Z3GGF7H")
    fun loadTrailers(
        @Path("id") id: Int
    ): Single<TrailerResponse>

    //    Загрузка отзывов
    @GET("review?token=1N0C0RF-8JF48H8-NHK5T0S-Z3GGF7H")
    fun loadReviews(
        @Query("movieId") id: Int
    ) : Single<ReviewResponse>
}