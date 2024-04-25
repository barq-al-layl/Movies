package com.phoenix.movies.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.phoenix.movies.data.api.MoviesApiService
import com.phoenix.movies.model.SearchItem

class SearchPaginationSource(
    private val service: MoviesApiService,
    private val title: String,
    private val pageLimit: Int,
) : PagingSource<Int, SearchItem>() {
    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> = try {
        val page = params.key ?: 1
        val response = service.searchTitle(
            title = title,
            page = page,
            limit = pageLimit,
        )
        LoadResult.Page(
            data = response.results.map { it.toSearchItem() },
            prevKey = page.minus(1).takeUnless { page == 1 },
            nextKey = page.plus(1).takeUnless { response.results.isEmpty() },
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

}