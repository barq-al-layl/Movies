package com.phoenix.movies.data.api.dto

data class Result(
    val id: String,
    val image: Image?,
    val title: String,
    val titleType: String,
    val year: Int?,
)