package com.phoenix.movies.data.api.dto

data class Ratings(
    val canRate: Boolean,
    val rating: Double,
    val ratingCount: Int,
)