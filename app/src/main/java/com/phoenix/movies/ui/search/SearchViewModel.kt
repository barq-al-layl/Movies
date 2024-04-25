package com.phoenix.movies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.phoenix.movies.data.api.MoviesApiService
import com.phoenix.movies.data.repository.SearchPaginationSource
import com.phoenix.movies.model.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val service: MoviesApiService,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val results = MutableStateFlow(PagingData.empty<SearchItem>())

    fun onSearchQueryValueChange(value: String) {
        _searchQuery.update { value }
    }

    fun getSearchResult() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 8),
                pagingSourceFactory = {
                    SearchPaginationSource(service, searchQuery.value, pageLimit = 8)
                },
            ).flow
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { data ->
                    results.update { data }
                }
        }
    }
}