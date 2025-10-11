package com.example.movies

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("docs")
    val reviews: List<Review>
)
