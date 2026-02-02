package com.sergokuzneczow.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sergokuzneczow.home.databinding.FragmentHomeBinding
import com.sergokuzneczow.home.ui.PostsRecyclerAdapter
import com.sergokuzneczow.model.Sorting
import com.sergokuzneczow.navigator.NavigatorApi
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: HomeFragmentViewModel.Factory

    @Inject
    lateinit var postsRecyclerAdapterFactory: PostsRecyclerAdapter.Factory

    @Inject
    lateinit var navigatorApi: NavigatorApi

    private lateinit var binding: FragmentHomeBinding

    private val args: HomeFragmentArgs by navArgs()

    private val vm: HomeFragmentViewModel by viewModels { HomeFragmentViewModel.Factory.provideFactory(viewModelFactory, args.sorting) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val postsRecyclerAdapter: PostsRecyclerAdapter = postsRecyclerAdapterFactory.create(onClick = { key -> vm.dispatch(HomeFragmentIntent.SelectPost(key)) })
        binding.rvPosts.adapter = postsRecyclerAdapter

        vm.container.stateFlow.onEach { state ->
            when (state) {
                HomeFragmentState.Loading -> {
                }

                is HomeFragmentState.Success -> {
                    postsRecyclerAdapter.submitList(state.posts)
                    binding.btnOpenModalBottomSheetSorting.text = state.sortingTitle
                }
            }
        }.launchWhenLifecycleStateStarted()

        vm.container.sideEffectFlow.onEach { action ->
            when (action) {
                HomeFragmentAction.ToHomeFragmentWithDefaultSort -> {
                    if (args.sorting != Sorting.DEFAULT) navigatorApi.toHome(findNavController(), Sorting.DEFAULT)
                }

                HomeFragmentAction.ToHomeFragmentWithDataSort -> {
                    if (args.sorting != Sorting.DATE) navigatorApi.toHome(findNavController(), Sorting.DATE)
                }

                is HomeFragmentAction.ToSortingModalBottomSheet -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToModalBottomSheet(action.sorting)
                    findNavController().navigate(action)
                }

                is HomeFragmentAction.ToDetails -> {
                    navigatorApi.toDetails(findNavController(), action.postKey)
                }
            }
        }.launchWhenLifecycleStateStarted()

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_menu_update -> {
                    vm.dispatch(HomeFragmentIntent.UpdateNow)
                    true
                }

                else -> false
            }
        }

        binding.btnOpenModalBottomSheetSorting.setOnClickListener {
            vm.dispatch(HomeFragmentIntent.OpenSortingModalBottomSheet)
        }
    }

    private fun Flow<Any>.launchWhenLifecycleStateStarted() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@launchWhenLifecycleStateStarted.launchIn(this)
            }
        }
    }
}