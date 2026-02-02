package com.sergokuzneczow.home

import com.sergokuzneczow.model.Post
import com.sergokuzneczow.model.Sorting

internal sealed interface HomeFragmentState {
    data object Loading : HomeFragmentState
    data class Success(
        val posts: List<Post>,
        val sortingTitle: String
    ) : HomeFragmentState
}

internal sealed interface HomeFragmentAction {
    data object ToHomeFragmentWithDataSort : HomeFragmentAction
    data object ToHomeFragmentWithDefaultSort : HomeFragmentAction
    data class ToSortingModalBottomSheet(val sorting: Sorting) : HomeFragmentAction
    data class ToDetails(val postKey: String) : HomeFragmentAction
}

internal sealed interface HomeFragmentIntent {
    data class SetPosts(val posts: List<Post>) : HomeFragmentIntent
    data class SelectPost(val key: String) : HomeFragmentIntent
    data object UpdateNow : HomeFragmentIntent
    data object OpenSortingModalBottomSheet : HomeFragmentIntent
}