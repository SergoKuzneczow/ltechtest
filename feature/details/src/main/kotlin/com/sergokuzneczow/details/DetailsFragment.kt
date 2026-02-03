package com.sergokuzneczow.details

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
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import coil3.request.target
import com.sergokuzneczow.details.databinding.FragmentDetailsBinding
import com.sergokuzneczow.navigator.NavigatorApi
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Inject
    lateinit var viewModelFactory: DetailsFragmentViewModel.Factory

    @Inject
    lateinit var navigatorApi: NavigatorApi

    private lateinit var binding: FragmentDetailsBinding

    private val args: DetailsFragmentArgs by navArgs()

    private val vm: DetailsFragmentViewModel by viewModels { DetailsFragmentViewModel.Factory.provideFactory(viewModelFactory, args.postKey) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.container.sideEffectFlow.onEach { action ->
            when (action) {
                DetailsFragmentAction.ToBack -> navigatorApi.popBackStack(findNavController())
            }
        }.launchWhenLifecycleStateStarted()

        vm.container.stateFlow.onEach { state ->
            when (state) {
                DetailsFragmentState.Loading -> {}
                is DetailsFragmentState.Success -> {
                    binding.containerProgress.visibility = View.GONE
                    binding.containerPost.visibility = View.VISIBLE
                    binding.tvPostDate.text = state.postDate
                    binding.tvPostTitle.text = state.postTitle
                    binding.tvPostContent.text = state.postContent

                    val request = ImageRequest.Builder(requireContext())
                        .data(state.postImageUri)
                        .target(binding.ivPostPreview)
                        .placeholder(com.sergokuzneczow.ui.R.drawable.icon_image_placeholder)
                        .error(com.sergokuzneczow.ui.R.drawable.icon_image_placeholder)
                        .crossfade(true)
                        .build()
                    vm.imageLoader.enqueue(request)
                }

                DetailsFragmentState.Error -> {}
            }
        }.launchWhenLifecycleStateStarted()

        binding.topAppBar.setNavigationOnClickListener { vm.dispatch(DetailsFragmentIntent.ToBack) }
    }

    private fun Flow<Any>.launchWhenLifecycleStateStarted() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@launchWhenLifecycleStateStarted.launchIn(this)
            }
        }
    }
}