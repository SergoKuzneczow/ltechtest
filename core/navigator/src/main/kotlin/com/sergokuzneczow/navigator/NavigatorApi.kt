package com.sergokuzneczow.navigator

import androidx.navigation.NavController
import com.sergokuzneczow.model.Sorting

public interface NavigatorApi {
    public fun popBackStack(navController: NavController)
    public fun toHome(navController: NavController, sorting: Sorting = Sorting.DEFAULT)
    public fun toDetails(navController: NavController, postKey: String)
}