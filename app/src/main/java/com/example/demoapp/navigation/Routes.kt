package com.example.demoapp.navigation

import com.example.demoapp.navigation.RouteArgs.productId
import com.example.demoapp.navigation.RouteArgs.productQrCode
import com.example.demoapp.navigation.Screens.Dashboard_screen
import com.example.demoapp.navigation.Screens.EquipmentDetails_screen
import com.example.demoapp.navigation.Screens.Equipments_screen
import com.example.demoapp.navigation.Screens.History_screen
import com.example.demoapp.navigation.Screens.Inventory_product_details_screen
import com.example.demoapp.navigation.Screens.Inventory_product_edit_details_screen
import com.example.demoapp.navigation.Screens.Inventory_report_screen
import com.example.demoapp.navigation.Screens.Inventory_screen
import com.example.demoapp.navigation.Screens.Inventory_summary_screen
import com.example.demoapp.navigation.Screens.Login_screen
import com.example.demoapp.navigation.Screens.Register_screen
import com.example.demoapp.navigation.Screens.UserDetails_screen
import com.example.demoapp.navigation.Screens.Welcome_screen

object Screens {
    const val Welcome_screen = "welcome"
    const val Login_screen = "login"
    const val Register_screen = "register"

    const val Dashboard_screen = "dashboard"
    const val UserDetails_screen = "userDetails"
    const val History_screen = "history"

    const val Equipments_screen = "equipments"
    const val EquipmentDetails_screen = "equipment"

    const val Inventory_screen = "Inventory_screen"
    const val Inventory_product_details_screen = "Inventory_product_details_screen"
    const val Inventory_product_edit_details_screen = "Inventory_product_edit_details_screen"
    const val Inventory_summary_screen = "Inventory_summary_screen"
    const val Inventory_report_screen = "Inventory_report_screen"
}

object RouteArgs {
    const val productId = "productId"
    const val productQrCode = "productQrCode"
    const val NoQrCode = "NoQrCode"
}

object Routes {
    const val Welcome = Welcome_screen
    const val Login = Login_screen
    const val Register = Register_screen

    const val Main = "main"
    const val Dashboard = Dashboard_screen
    const val UserDetails = UserDetails_screen
    const val History = History_screen
    const val Equipments = Equipments_screen
    const val EquipmentDetails = "$EquipmentDetails_screen/{$productId}"

    const val Inventory = Inventory_screen
    const val InventoryProductDetails = "$Inventory_product_details_screen/{$productQrCode}"
    const val InventoryProductEditDetails = "$Inventory_product_edit_details_screen/{$productId}"
    const val InventorySummary = Inventory_summary_screen
    const val InventoryReport = Inventory_report_screen

}