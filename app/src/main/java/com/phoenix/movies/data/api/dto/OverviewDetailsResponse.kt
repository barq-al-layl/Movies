package com.phoenix.movies.data.api.dto

data class OverviewDetailsResponse(
    val id: String,
    val title: Title,
    val ratings: Ratings,
    val genres: List<String>,
    val releaseDate: String,
    val plotOutline: PlotOutline,
    val plotSummary: PlotSummary,
)