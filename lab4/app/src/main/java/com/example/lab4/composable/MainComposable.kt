package com.example.lab4.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lab4.R
import com.example.lab4.navigation.BottomBarItems
import com.example.lab4.navigation.Routes

@Composable
fun MainComposable() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Routes(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val bottomItems = listOf(
        BottomBarItems.Home,
        BottomBarItems.Profile,
        BottomBarItems.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        bottomItems.forEach { bottomItem ->
            AddItem(
                item = bottomItem,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    item: BottomBarItems,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = item.title)
        },
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = stringResource(id = R.string.navigation)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == item.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}
