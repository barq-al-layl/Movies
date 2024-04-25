package com.phoenix.movies.data.repository

import com.phoenix.movies.data.api.dto.OverviewDetailsResponse
import com.phoenix.movies.data.api.dto.Result
import com.phoenix.movies.model.SearchItem
import com.phoenix.movies.model.TitleDetails

fun Result.toSearchItem() = SearchItem(
    // id format as follows  "/title/tt2084970/"
    id = id.split('/')[2],
    title = title,
    type = titleType,
    imageUrl = image?.url,
    year = year,
)

fun OverviewDetailsResponse.toTitleDetails() = TitleDetails(
    // id format as follows  "/title/tt2084970/"
    id = id.split('/')[2],
    imageUrl = title.image?.url,
    runningTimeInMinutes = title.runningTimeInMinutes,
    numberOfEpisodes = title.numberOfEpisodes,
    seriesStartYear = title.seriesStartYear,
    seriesEndYear = title.seriesEndYear,
    title = title.title,
    titleType = title.titleType,
    year = title.year,
    rating = ratings.rating,
    ratingCount = ratings.ratingCount,
    genres = genres,
    releaseDate = releaseDate,
    plotOutline = plotOutline.text,
    plotSummary = plotSummary.text,
)