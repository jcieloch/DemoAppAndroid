package com.example.demoapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.demoapp.R

@Composable
fun BackTopAppBar(onBack: () -> Unit, text: String) {
    TopAppBar(
        title = {
            Text(text = text)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "go back")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}


data class FilterAction(
    val text: String, val action: () -> Unit
)

@Composable
fun BackFilterTopAppBar(
    onBack: () -> Unit,
//    onFilter: (filters: List<T>) -> Unit,
    filterActions: List<FilterAction>,
    text: String
) {
    TopAppBar(
        title = {
            Text(text = text)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "go back")
            }
        },
        actions = {
            TopAppBarDropdownMenu { closeMenu ->
                filterActions.forEach {
                    DropdownMenuItem(onClick = { it.action(); closeMenu() }) {
                        Text(text = it.text)
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun TopAppBarDropdownMenu(
    content: @Composable ColumnScope.(() -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(painterResource(id = R.drawable.filter), "more")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            content { expanded = !expanded }
        }
    }
}

@Composable
fun TopAppBar(openDrawer: () -> Unit, text: String) {
    TopAppBar(
        title = { Text(text = text) },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Filled.Menu, "open drawer")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}