package com.sergokuzneczow.navigator

import androidx.navigation.NavController
import com.sergokuzneczow.model.Sorting

public interface NavigatorApi {
    public fun toHome(navController: NavController, sorting: Sorting = Sorting.DEFAULT)
}