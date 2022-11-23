package com.engie.eea_tech_interview.view

sealed class Screen(val route: String, val deepLink: String = "") {
    object HomeScreen: Screen(route = "home_screen", deepLink = "")
    object DetailScreen: Screen(route = "detail_screen", deepLink = "detail_screen/{movie}")
}
