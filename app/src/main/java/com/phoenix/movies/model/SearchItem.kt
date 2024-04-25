package com.phoenix.movies.model

data class SearchItem(
    val id: String,
    val title: String,
    val type: String,
    val imageUrl: String?,
    val year: Int?,
) {
    val formattedType = type
        .replaceFirstChar { it.uppercase() }
        .split("""(?=\\p{Upper})""")
        .joinToString(" ")
}
