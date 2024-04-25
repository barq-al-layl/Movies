package com.phoenix.movies.ui

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.phoenix.movies.ui.navigation.BottomBarDestination
import com.phoenix.movies.ui.theme.Roboto
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun MoviesApp() {

    val navController = rememberNavController()

    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    var isBottomBarVisible by remember {
        mutableStateOf(
            BottomBarDestination.values().any {
                it.direction == currentDestination
            }
        )
    }
    LaunchedEffect(currentDestination) {
        isBottomBarVisible = BottomBarDestination.values().any {
            it.direction == currentDestination
        }
    }
    val navigationBarColor =
        MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp).takeIf { isBottomBarVisible }
            ?: MaterialTheme.colorScheme.background
    val view = LocalView.current
    LaunchedEffect(navigationBarColor) {
        val window = (view.context as Activity).window
        window.navigationBarColor = navigationBarColor.toArgb()
    }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                CustomBottomBar(navController, currentDestination.route)
            }
        },
    ) { innerPadding ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            modifier = Modifier.padding(innerPadding),
            navController = navController,
        )
    }
}

@Composable
private fun CustomBottomBar(
    navController: NavController,
    currentRoute: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BottomBarDestination.values().forEach { destination ->
            CustomBottomBarItem(
                icon = destination.icon,
                label = stringResource(id = destination.label),
                isSelected = destination.direction.route == currentRoute
            ) {
                navController.navigate(destination.direction) {
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
private fun CustomBottomBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = MaterialTheme.colorScheme.primary.takeIf { isSelected }
        ?: MaterialTheme.colorScheme.onBackground
    val backgroundColor = contentColor.copy(alpha = .15f).takeIf { isSelected }
        ?: Color.Transparent

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = contentColor,
        )
        AnimatedVisibility(visible = isSelected) {
            Text(
                text = label,
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                color = contentColor,
            )
        }
    }
}