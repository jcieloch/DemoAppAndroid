package com.example.demoapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DrawerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    logout: () -> Unit,
    navigateToUserDetails: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                currentRoute = currentRoute,
                navigateToUserDetails = navigateToUserDetails,
                logout = {
                    logout()
                },
                closeDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        }
    ) {
        content()
    }
}

@Composable
private fun Drawer(
    currentRoute: String,
    navigateToUserDetails: () -> Unit,
    logout: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        DrawerHeader(
            imageVector = Icons.Filled.Home,
            contentDescription = "Drawer"
        )
        DrawerButton(
            imageVector = Icons.Filled.AccountCircle,
            label = "Account details",
            action = {
                navigateToUserDetails()
//                navigateToTasks()
                closeDrawer()
            }
        )
        DrawerButton(
            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
            label = "logout",
            action = {
                logout()
//                navigateToStatistics()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun DrawerHeader(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.width(20.dp)
        )
        Text(
            text = "drawer",
            color = MaterialTheme.colors.surface
        )
    }
}

@Composable
private fun DrawerButton(
    imageVector: ImageVector,
    label: String,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = false
    val tintColor = if (isSelected) {
        MaterialTheme.colors.secondary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }

    TextButton(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector,
                label
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
                color = tintColor
            )
        }
    }
}
