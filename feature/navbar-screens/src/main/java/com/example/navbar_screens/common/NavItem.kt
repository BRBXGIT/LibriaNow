package com.example.navbar_screens.common

import com.example.design_system.theme.LibriaNowIcons
import com.example.navbar_screens.home_screen.navigation.HomeScreenRoute
import com.example.navbar_screens.likes_screen.navigation.LikesScreenRoute
import com.example.navbar_screens.more_screen.navigation.MoreScreenRoute
import com.example.navbar_screens.search_screen.navigation.SearchScreenRoute

data class NavItem(
    val label: String,
    val icon: Int, // Icon already animated from filled to outlined
    val destination: Any
)

val navItems = listOf(
    NavItem(
        label = "Аниме",
        icon = LibriaNowIcons.HomeAnimated,
        destination = HomeScreenRoute
    ),
    NavItem(
        label = "Избранное",
        icon = LibriaNowIcons.HeartAnimated,
        destination = LikesScreenRoute
    ),
    NavItem(
        label = "Поиск",
        icon = LibriaNowIcons.MagnifierAnimated,
        destination = SearchScreenRoute
    ),
    NavItem(
        label = "Больше",
        icon = LibriaNowIcons.MoreAnimated,
        destination = MoreScreenRoute
    )
)