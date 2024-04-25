package com.phoenix.movies.data.repository

import com.phoenix.movies.data.api.MoviesApiService
import com.phoenix.movies.model.TitleDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TitleDetailsRepository @Inject constructor(
    private val service: MoviesApiService,
) {
    fun getOverviewDetails(id: String): Flow<TitleDetails> = flow {
        val data = service.getOverviewDetails(id).toTitleDetails()
        emit(data)
    }
}