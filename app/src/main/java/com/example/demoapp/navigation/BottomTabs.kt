package com.example.demoapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.demoapp.navigation.NavBarItems.navBarItems
import com.example.demoapp.ui.auth.AuthViewModel
import com.example.demoapp.ui.dashboard.DashboardScreen
import com.example.demoapp.ui.history.HistoryScreen

@Composable
fun BottomTabs(
    navigateToProductsList: () -> Unit,
    navigateToInventory: () -> Unit,
    navigateToInventorySummary: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.semantics { contentDescription = "Navigation bar" }) {
                navBarItems.forEach { screen ->
                    NavigationBarItem(selected = currentBackStackEntry?.destination?.hierarchy?.any {
                        it.route == screen.route
                    } == true, onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, label = {
                        Text(text = screen.title)
                    }, alwaysShowLabel = true, icon = {
                        Icon(imageVector = screen.icon, contentDescription = screen.title)
                    })
                }
            }
        }) { paddingValues ->
        BottomTabs(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            openDrawer = openDrawer,
            navigateToProductsList = navigateToProductsList,
            navigateToInventory = navigateToInventory,
            navigateToInventorySummary = navigateToInventorySummary
        )
    }
}

@Composable
fun BottomTabs(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    openDrawer: () -> Unit,
    navigateToProductsList: () -> Unit,
    navigateToInventory: () -> Unit,
    navigateToInventorySummary: () -> Unit
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = Routes.Dashboard
    ) {
        composable(route = Routes.Dashboard) {
            val authViewModel: AuthViewModel = hiltViewModel()
            DashboardScreen(
                viewModel = authViewModel,
                openDrawer = openDrawer,
                navigateToProductsList = navigateToProductsList,
                navigateToInventory = navigateToInventory,
                navigateToInventorySummary = navigateToInventorySummary
            )
        }
        composable(route = Routes.History) {
            HistoryScreen()
        }
    }
}

object NavBarItems {
    val navBarItems = listOf(
        NavBarItem(
            "Home",
            Routes.Dashboard,
            Icons.Filled.Home
        ),
        NavBarItem(
            "History",
            Routes.History,
            Icons.Filled.Star
        )
    )
}

data class NavBarItem(val title: String, val route: String, val icon: ImageVector)