package com.sergokuzneczow.ltechtest.navigator

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.sergokuzneczow.ltechtest.GraphMainDirections
import com.sergokuzneczow.navigator.NavigatorApi

internal class NavigatorImpl : NavigatorApi {
    override fun toHome(navController: NavController) {
        val action: NavDirections = GraphMainDirections.actionToFeatureAuthorization()
        navController.navigate(action)
    }
}