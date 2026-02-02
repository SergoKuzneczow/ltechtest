package com.sergokuzneczow.ltechtest.navigator

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.sergokuzneczow.ltechtest.GraphMainDirections
import com.sergokuzneczow.model.Sorting
import com.sergokuzneczow.navigator.NavigatorApi
import jakarta.inject.Inject

internal class NavigatorImpl @Inject constructor() : NavigatorApi {

    override fun popBackStack(navController: NavController) {
        navController.popBackStack()
    }

    override fun toHome(navController: NavController, sorting: Sorting) {
        val action: NavDirections = GraphMainDirections.actionToFeatureHome(sorting)
        navController.navigate(action)
    }

    override fun toDetails(navController: NavController, postKey: String) {
        val action = GraphMainDirections.actionToFeatureDetails(postKey)
        navController.navigate(action)
    }
}