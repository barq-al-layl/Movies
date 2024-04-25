package com.phoenix.movies.data.api

import com.phoenix.movies.data.api.dto.OverviewDetailsResponse
import com.phoenix.movies.data.api.dto.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    

    @GET("title/v2/find")
    suspend fun searchTitle(
        @Query("title") title: String,
        @Query("limit") limit: Int = 20,
        @Query("titleType") type: String = "movie",
        @Query("sortArg") sortedBy: String = "moviemeter,asc",
        @Query("paginationKey") page: Int? = null,
    ): SearchResponse

    @GET("title/get-overview-details")
    suspend fun getOverviewDetails(
        @Query("tconst") titleId: String,
    ): OverviewDetailsResponse

}
