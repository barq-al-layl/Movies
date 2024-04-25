package com.phoenix.movies.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.LocalMovies
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.phoenix.movies.R
import com.phoenix.movies.model.TitleDetails
import com.phoenix.movies.ui.theme.LobsterTwo
import com.phoenix.movies.ui.theme.Roboto
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

private enum class OverviewTabs {
    About, Reviews, Cast,
}

@OptIn(ExperimentalMaterial3Api::class)
@Destination(
    navArgsDelegate = TitleDetailsScreenNavArgs::class,
)
@Composable
fun TitleDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: TitleDetailsViewModel = hiltViewModel(),
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val data by viewModel.data.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 12.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.details),
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
                    IconButton(onClick = viewModel::onWatchListClick) {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(46.dp))
            } else {
                data?.let { TitleDetailsScreenContent(it) }
            }
        }
    }
}

@Composable
private fun TitleDetailsScreenContent(data: TitleDetails) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SubcomposeAsyncImage(
            model = data.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Fit,
            filterQuality = FilterQuality.Low,
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
            text = data.title,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(imageVector = Icons.Rounded.CalendarToday, contentDescription = null)
                Text(
                    text = "${data.year}",
                    fontWeight = FontWeight.Medium,
                )
            }
            Spacer(
                modifier = Modifier
                    .size(width = 4.dp, height = 24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = null)
                Text(
                    text = "${data.runningTimeInMinutes} Minutes",
                    fontWeight = FontWeight.Medium,
                )
            }
            Spacer(
                modifier = Modifier
                    .size(width = 4.dp, height = 24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(imageVector = Icons.Rounded.Movie, contentDescription = null)
                Text(
                    text = data.genres.first(),
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        val (selectedTabIndex, onTabIndexChange) = remember { mutableIntStateOf(OverviewTabs.About.ordinal) }
        TabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            OverviewTabs.values().forEach {
                Tab(
                    selected = it.ordinal == selectedTabIndex,
                    onClick = { onTabIndexChange(it.ordinal) },
                    text = { Text(text = it.name) },
                )
            }
        }
        Box(modifier = Modifier.heightIn(min = 600.dp)) {
            when (selectedTabIndex) {
                OverviewTabs.About.ordinal -> AboutTitleSection(text = data.plotSummary)

                OverviewTabs.Reviews.ordinal -> {

                }

                OverviewTabs.Cast.ordinal -> {

                }
            }
        }
    }
}

@Composable
private fun AboutTitleSection(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
    )
}