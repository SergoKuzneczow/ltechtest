package com.sergokuzneczow.details

internal sealed interface DetailsFragmentState {

    data object Loading : DetailsFragmentState

    data class Success(
        val postTitle: String,
        val postDate: String,
        val postImageUri: String,
        val postContent: String,
    ) : DetailsFragmentState

    data object Error : DetailsFragmentState
}

internal sealed interface DetailsFragmentAction {
    data object ToBack : DetailsFragmentAction
}

internal sealed interface DetailsFragmentIntent {
    data class SetPostData(
        val postTitle: String,
        val postDate: String,
        val postImageUri: String,
        val postContent: String,
    ) : DetailsFragmentIntent

    data object ToBack : DetailsFragmentIntent
}