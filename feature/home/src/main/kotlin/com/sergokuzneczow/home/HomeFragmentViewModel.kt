package com.sergokuzneczow.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.database.api.DatabaseDataSourceApi
import com.sergokuzneczow.domain.phone_mask_converter_case.SyncNowPostSourcesCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.SyncPostSourcesCaseApi
import com.sergokuzneczow.home.HomeFragmentState.Success
import com.sergokuzneczow.model.Post
import com.sergokuzneczow.model.Sorting
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

internal class HomeFragmentViewModel @AssistedInject constructor(
    @Assisted private val sorting: Sorting,
    syncPostSourcesCaseApi: SyncPostSourcesCaseApi,
    database: DatabaseDataSourceApi,
    private val syncNowPostSourcesCaseApi: SyncNowPostSourcesCaseApi,
) : ViewModel(), ContainerHost<HomeFragmentState, HomeFragmentAction> {

    override val container: Container<HomeFragmentState, HomeFragmentAction> = viewModelScope.container(HomeFragmentState.Loading)

    init {
        syncPostSourcesCaseApi.execute(viewModelScope)

        database.getPostsBySorting(sorting)
            .distinctUntilChanged()
            .onEach { posts: List<Post> -> dispatch(HomeFragmentIntent.SetPosts(posts)) }
            .launchIn(viewModelScope)
    }

    fun dispatch(intent: HomeFragmentIntent) {
        when (intent) {
            is HomeFragmentIntent.SetPosts -> intent {
                val sortingTitle: String = when (sorting) {
                    Sorting.DEFAULT -> "По умолчанию"
                    Sorting.DATE -> "По дате"
                }
                reduce { Success(intent.posts, sortingTitle) }
            }

            is HomeFragmentIntent.SelectPost -> intent {
                postSideEffect(HomeFragmentAction.ToDetails(intent.key))
            }

            HomeFragmentIntent.UpdateNow -> {
                syncNowPostSourcesCaseApi.execute(viewModelScope)
            }

            HomeFragmentIntent.OpenSortingModalBottomSheet -> intent {
                postSideEffect(HomeFragmentAction.ToSortingModalBottomSheet(sorting))
            }
        }
    }

    @AssistedFactory
    interface Factory {

        companion object {
            fun provideFactory(
                assistedFactory: Factory,
                sorting: Sorting,
            ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(sorting) as T
                }
            }
        }

        fun create(sorting: Sorting): HomeFragmentViewModel
    }
}