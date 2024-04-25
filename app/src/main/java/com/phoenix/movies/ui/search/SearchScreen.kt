package com.phoenix.movies.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.LocalMovies
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.phoenix.movies.R
import com.phoenix.movies.model.SearchItem
import com.phoenix.movies.ui.destinations.TitleDetailsScreenDestination
import com.phoenix.movies.ui.theme.LobsterTwo
import com.phoenix.movies.ui.theme.Roboto
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val results = viewModel.results.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 12.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.search),
                        fontFamily = LobsterTwo,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(12.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        ) {
            item(
                span = { GridItemSpan(2) },
            ) {
                val keyboardController = LocalSoftwareKeyboardController.current
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::onSearchQueryValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    label = {
                        Text(text = stringResource(id = R.string.search))
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                results.refresh()
                                keyboardController?.hide()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = null,
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            results.refresh()
                            keyboardController?.hide()
                        },
                    ),
                    singleLine = true,
                )
            }
            items(items = results.itemSnapshotList) {
                it?.let {
                    SearchResultItem(it) {
                        navigator.navigate(TitleDetailsScreenDestination(it.id)) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    item: SearchItem,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SubcomposeAsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .height(240.dp)
                .padding(4.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Fit,
            filterQuality = FilterQuality.Low,
            loading = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(60.dp))
                }
            },
            error = {
                Surface(
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = 2.dp,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocalMovies,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                    )
                }
            },
        )
        Text(
            text = item.title,
            fontFamily = Roboto,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = item.year?.toString() ?: "N/A",
            fontFamily = Roboto,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}