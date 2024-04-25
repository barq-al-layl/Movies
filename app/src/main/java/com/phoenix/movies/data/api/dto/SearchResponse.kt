package com.phoenix.movies.data.api.dto

data class SearchResponse(
    val paginationKey: Int,
    val results: List<Result>,
    val totalMatches: Int,
)