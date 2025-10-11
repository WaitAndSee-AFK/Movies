package com.example.movies

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("type")
    val type: String,
    @SerializedName("review")
    val review: String,
    @SerializedName("author")
    val author: String
)
