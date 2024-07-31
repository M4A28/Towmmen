package com.mohmmed.mosa.eg.towmmen.presenter.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToScreen

@Composable
fun DrawerScreen(
    navController: NavController,
){
    var selectedNavigationItem by remember { mutableStateOf(NavigationItem.Home) }
    DrawerContent(
        navController = navController,
        selectedNavigationItem = selectedNavigationItem,
        onNavigationItemClick = {
            selectedNavigationItem = it
        },
    )
}


@Composable
fun DrawerContent(navController: NavController,
                  selectedNavigationItem: NavigationItem,
                  onNavigationItemClick: (NavigationItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp))
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth(fraction = 0.7f)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {

        }
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "Image"
        )
        Spacer(modifier = Modifier.height(40.dp))
        NavigationItem.entries.toTypedArray().take(4).forEach { navigationItem ->
            NavigationDrawerItem(
                navigationItem = navigationItem,
                selected = navigationItem == selectedNavigationItem,
                onClick = {
                    onNavigationItemClick(navigationItem)
                    when(navigationItem){
                        NavigationItem.Home -> {
                            navigateToTab(navController, Route.HomeScreen.route)
                        }
                        NavigationItem.Settings -> {
                            navigateToScreen(navController, Route.SettingScreen.route)
                        }
                        NavigationItem.Profit ->
                            navigateToScreen(navController, Route.ProfitScreen.route)
                        NavigationItem.Category ->
                            navigateToScreen(navController, Route.CategoryScreen.route)
                        else -> {}
                    }

                }
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Spacer(modifier = Modifier.weight(1f))

        NavigationItem.entries.toTypedArray().takeLast(2).forEach { navigationItem ->
            NavigationDrawerItem(
                navigationItem = navigationItem,
                selected =  false,
                onClick = {
                    onNavigationItemClick(navigationItem)
                               when (navigationItem) {
                                   NavigationItem.Share -> {

                                   }
                                   NavigationItem.About -> {
                                       navigateToScreen(navController, Route.AboutScreen.route)
                                   }

                                   else -> {}
                               }
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }

}

private fun navigateToTab(navController: NavController, route: String){
    navController.navigate(route = route){
        navController.graph.startDestinationRoute?.let{
            popUpTo(it){ saveState = true }
            restoreState = true
            launchSingleTop = true
        }
    }
}