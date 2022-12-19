package com.khoerulih.bookpedia.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object ReadList: Screen("readlist")
    object Profile: Screen("profile")
    object DetailBook: Screen("home/{id}"){
        fun createRoute(id: Long) = "home/$id"
    }
}