package com.example.demoapp.navigation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demoapp.navigation.RouteArgs.NoQrCode
import com.example.demoapp.navigation.RouteArgs.productId
import com.example.demoapp.navigation.RouteArgs.productQrCode
import com.example.demoapp.navigation.Routes.InventoryProductDetails
import com.example.demoapp.navigation.Routes.InventoryProductEditDetails
import com.example.demoapp.navigation.Routes.InventoryReport
import com.example.demoapp.navigation.Routes.InventorySummary
import com.example.demoapp.navigation.Routes.Main
import com.example.demoapp.navigation.Screens.EquipmentDetails_screen
import com.example.demoapp.navigation.Screens.Inventory_product_details_screen
import com.example.demoapp.navigation.Screens.Inventory_product_edit_details_screen
import com.example.demoapp.ui.auth.AuthViewModel
import com.example.demoapp.ui.auth.LoginScreen
import com.example.demoapp.ui.auth.RegisterScreen
import com.example.demoapp.ui.equipment.details.EquipmentDetailsScreen
import com.example.demoapp.ui.equipment.list.EquipmentsScreen
import com.example.demoapp.ui.inventory.details.InventoryProductDetailsScreen
import com.example.demoapp.ui.inventory.edit.InventoryProductEditDetailsScreen
import com.example.demoapp.ui.inventory.report.InventoryReportScreen
import com.example.demoapp.ui.inventory.scan.InventoryScanScreen
import com.example.demoapp.ui.inventory.summary.InventorySummaryScreen
import com.example.demoapp.ui.user_details.UserDetailsScreen
import com.example.demoapp.ui.welcome.WelcomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Routes.Main
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        authViewModel.isLoggedIn.collect { isLoggedIn ->
            Log.d(TAG, "Login state -> $isLoggedIn")
            if (!isLoggedIn) {
                navController.popBackStack()
                navController.navigate(Routes.Welcome)
            }
        }
    }
    NavHost(navController = navController, startDestination = Routes.Main) {
        authGraph(navController, authViewModel, coroutineScope)
        mainGraph(navController, authViewModel, coroutineScope, drawerState, currentRoute)
    }
}

private fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    coroutineScope: CoroutineScope
) {
    composable(route = Routes.Welcome) {
        WelcomeScreen(
            viewModel = authViewModel,
            onLoggedIn = {
                navController.popBackStack()
                navController.navigate(Routes.Main)
            },
            onLoginPress = {
                navController.navigate(Routes.Login)
            },
            onRegisterPress = {
                navController.navigate(Routes.Register)
            }
        )
    }
    composable(route = Routes.Login) {
        LoginScreen(
            onLogin = { loginUser ->
                coroutineScope.launch {
                    authViewModel.login(loginUser = loginUser)
                    navController.popBackStack()
                }
            }
        )
    }
    composable(route = Routes.Register) {
        RegisterScreen(
            onRegister = { registerUser ->
                coroutineScope.launch {
                    authViewModel.register(registerUser)
                    navController.popBackStack()
                }
            }
        )
    }
}

private fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    currentRoute: String
) {
    composable(route = Routes.Main) {
        AppModalDrawer(
            drawerState,
            currentRoute,
            navigateToUserDetails = {
                navController.navigate(Routes.UserDetails)
            },
            logout = {
                coroutineScope.launch {
                    authViewModel.logout()
                }
            }) {
            BottomTabs(
                openDrawer = { coroutineScope.launch { drawerState.open() } },
                navigateToProductsList = {
                    navController.navigate(Routes.Equipments)
                },
                navigateToInventory = {
                    navController.navigate(Routes.Inventory)
                },
                navigateToInventorySummary = {
                    navController.navigate(InventorySummary)
                }
            )
        }
    }
    composable(route = Routes.UserDetails) {
        UserDetailsScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }
    // TODO: do wydzielenia na osoby graf przeglądu sprzętu
    composable(route = Routes.Equipments) {
        EquipmentsScreen(
            onBack = {
                navController.popBackStack()
            },
            onEquipmentClick = { equipmentId ->
                navController.navigate("$EquipmentDetails_screen/$equipmentId")
            }
        )
    }
    composable(
        route = Routes.EquipmentDetails,
        arguments = listOf(navArgument(productId) { type = NavType.LongType })
    ) {
        EquipmentDetailsScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }
    // TODO: do wydzielenia ścieżka inwentaryzacji
    composable(
        route = Routes.Inventory,
    ) {
        InventoryScanScreen(
            onBack = {
                navController.popBackStack()
            },
            onBarcodeScanned = { qr ->
                navController.navigate("$Inventory_product_details_screen/$qr")
            },
            onAddManually = {
                navController.navigate("$Inventory_product_details_screen/$NoQrCode")
//                navController.navigate("$Inventory_product_details_screen/QR456")
            }
        )
    }
    composable(
        route = InventoryProductDetails,
        arguments = listOf(navArgument(productQrCode) { type = NavType.StringType; nullable = true })
    ) { entry ->
        InventoryProductDetailsScreen(
            onFinish = {
                navController.navigate(InventorySummary)
            },
            qr = entry.arguments?.getString(productQrCode),
            onAddNext = {
                navController.popBackStack()
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
    composable(
        route = InventorySummary
    ) { entry ->
        InventorySummaryScreen(
            onItemClick= { id ->
                navController.navigate("$Inventory_product_edit_details_screen/$id")
            },
            onBack = {
                navController.popBackStack()
            },
            onReport = {
                navController.navigate(InventoryReport)
            }
        )
    }
    composable(
        route = InventoryReport
    ) { _ ->
        InventoryReportScreen(
            onBack = {
                navController.navigate(Main)
            }
        )
    }
    composable(
        route = InventoryProductEditDetails,
        arguments = listOf(navArgument(productId) { type = NavType.LongType; nullable = false })
    ) { entry ->
        InventoryProductEditDetailsScreen(
            onSave = {
                navController.popBackStack()
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
