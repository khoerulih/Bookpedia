package com.khoerulih.bookpedia

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.khoerulih.bookpedia.ui.navigation.NavigationItem
import com.khoerulih.bookpedia.ui.navigation.Screen
import com.khoerulih.bookpedia.ui.screen.detail.DetailScreen
import com.khoerulih.bookpedia.ui.screen.home.HomeScreen
import com.khoerulih.bookpedia.ui.screen.profile.ProfileScreen
import com.khoerulih.bookpedia.ui.screen.readlist.ReadListScreen
import com.khoerulih.bookpedia.ui.theme.BookpediaTheme

@Composable
fun BookpediaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != Screen.DetailBook.route && currentRoute != Screen.Home.route) {
                MyTopBar(screenName = currentRoute)
            }
        },
        bottomBar = {
            if (currentRoute != Screen.DetailBook.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailBook.createRoute(id))
                    }
                )
            }
            composable(Screen.ReadList.route) {
                ReadListScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailBook.createRoute(id))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailBook.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                val id = it.arguments?.getLong("id") ?: -1L
                DetailScreen(
                    id = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToReadList = {
                        navController.popBackStack()
                        navController.navigate(Screen.ReadList.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MyTopBar(screenName: String?) {
    TopAppBar(
        title = {
            when (screenName) {
                "readlist" -> {
                    Text(text = "Read List")
                }
                "profile" -> {
                    Text(text = "Profile")
                }
                else -> {
                    Text("Bookpedia")
                }
            }
        }
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_read_list),
                icon = Icons.Outlined.List,
                screen = Screen.ReadList
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookpediaPreview() {
    BookpediaTheme {
        BookpediaApp()
    }
}