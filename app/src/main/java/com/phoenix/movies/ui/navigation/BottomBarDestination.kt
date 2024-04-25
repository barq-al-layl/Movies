package com.phoenix.movies.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.phoenix.movies.R
import com.phoenix.movies.ui.destinations.HomeScreenDestination
import com.phoenix.movies.ui.destinations.SearchScreenDestination
import com.phoenix.movies.ui.destinations.WatchlistScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int,
) {
    Home(HomeScreenDestination, Icons.Rounded.Home, R.string.home),
    Search(SearchScreenDestination, Icons.Rounded.Search, R.string.search),
    Watchlist(WatchlistScreenDestination, Icons.Rounded.Bookmark, R.string.watchlist),
}