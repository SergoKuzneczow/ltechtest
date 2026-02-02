package com.sergokuzneczow.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.database.api.DatabaseDataSourceApi
import com.sergokuzneczow.details.DetailsFragmentState.Success
import com.sergokuzneczow.domain.phone_mask_converter_case.ConverterToTimePatterCaseApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

internal class DetailsFragmentViewModel @AssistedInject constructor(
    @Assisted postKey: String,
    database: DatabaseDataSourceApi,
    converterToTimePatterCaseApi: ConverterToTimePatterCaseApi,
) : ViewModel(), ContainerHost<DetailsFragmentState, DetailsFragmentAction> {

    override val container: Container<DetailsFragmentState, DetailsFragmentAction> = viewModelScope.container(DetailsFragmentState.Loading)

    init {
        database.getPostsByKey(postKey).map {
            it.firstOrNull() ?: throw IllegalStateException("Not find post column by key=$postKey")
        }.onEach {
            dispatch(
                DetailsFragmentIntent.SetPostData(
                    postTitle = it.title,
                    postDate = converterToTimePatterCaseApi.execute(it.date),
                    postImageUri = it.imageUrl,
                    postContent = it.text,
                )
            )
        }.launchIn(viewModelScope + Dispatchers.IO)
    }

    fun dispatch(intent: DetailsFragmentIntent) {
        when (intent) {
            is DetailsFragmentIntent.SetPostData -> intent {
                reduce {
                    Success(
                        postTitle = intent.postTitle,
                        postDate = intent.postDate,
                        postImageUri = intent.postImageUri,
                        postContent = intent.postContent,
                    )
                }
            }

            DetailsFragmentIntent.ToBack -> intent {
                postSideEffect(DetailsFragmentAction.ToBack)
            }
        }
    }

    @AssistedFactory
    interface Factory {

        companion object {
            fun provideFactory(
                assistedFactory: Factory,
                postKey: String,
            ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(postKey) as T
                }
            }
        }

        fun create(postKey: String): DetailsFragmentViewModel
    }
}