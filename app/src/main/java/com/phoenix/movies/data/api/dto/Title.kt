package com.phoenix.movies.data.api.dto

data class Title(
    val id: String,
    val image: Image?,
    val runningTimeInMinutes: Int,
    val numberOfEpisodes: Int?,
    val seriesEndYear: Int?,
    val seriesStartYear: Int?,
    val title: String,
    val titleType: String,
    val year: Int,
)