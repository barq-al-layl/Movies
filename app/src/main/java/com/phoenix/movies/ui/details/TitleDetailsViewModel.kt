package com.phoenix.movies.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.movies.data.repository.TitleDetailsRepository
import com.phoenix.movies.ui.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TitleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: TitleDetailsRepository,
) : ViewModel() {

    private val titleId = savedStateHandle.navArgs<TitleDetailsScreenNavArgs>().id

    val data = repository.getOverviewDetails(titleId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null,
    )

    val isLoading = data.map { it == null }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = true,
    )

    fun onWatchListClick() {

    }
}