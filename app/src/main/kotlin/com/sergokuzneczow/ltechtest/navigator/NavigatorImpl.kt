package com.sergokuzneczow.ltechtest.navigator

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.sergokuzneczow.ltechtest.GraphMainDirections
import com.sergokuzneczow.navigator.NavigatorApi
import jakarta.inject.Inject

internal class NavigatorImpl @Inject constructor() : NavigatorApi {
    override fun toHome(navController: NavController) {
        val action: NavDirections = GraphMainDirections.actionToFeatureHome()
        navController.navigate(action)
    }
}