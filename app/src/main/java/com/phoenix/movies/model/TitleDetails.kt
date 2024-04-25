package com.phoenix.movies.model

data class TitleDetails(
    val id: String,
    val imageUrl: String?,
    val runningTimeInMinutes: Int,
    val numberOfEpisodes: Int?,
    val seriesEndYear: Int?,
    val seriesStartYear: Int?,
    val title: String,
    val titleType: String,
    val year: Int,
    val rating: Double,
    val ratingCount: Int,
    val genres: List<String>,
    val releaseDate: String,
    val plotOutline: String,
    val plotSummary: String,
)
