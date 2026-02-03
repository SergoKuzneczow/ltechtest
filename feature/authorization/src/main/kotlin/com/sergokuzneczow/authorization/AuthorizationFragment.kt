package com.sergokuzneczow.authorization

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter.LengthFilter
import android.text.Selection
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sergokuzneczow.authorization.AuthorizationFragmentIntent.ChangePasswordTextField
import com.sergokuzneczow.authorization.AuthorizationFragmentIntent.ChangePhoneTextField
import com.sergokuzneczow.authorization.databinding.FragmentAuthorizationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
internal class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private lateinit var binding: FragmentAuthorizationBinding
    private val vm: AuthorizationViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.container.sideEffectFlow.onEach { action ->
            when (action) {
                AuthorizationFragmentAction.ToHome -> vm.navigatorApi.toHome(findNavController())
            }
        }.launchWhenLifecycleStateStarted()

        vm.container.stateFlow.onEach { state ->
            when (state) {
                AuthorizationFragmentState.Loading -> {
                    binding.cpbLoading.visibility = View.VISIBLE
                    binding.containerSuccess.visibility = View.GONE
                    binding.containerReconnect.visibility = View.GONE
                }

                is AuthorizationFragmentState.Success -> {
                    binding.cpbLoading.visibility = View.GONE
                    binding.containerSuccess.visibility = View.VISIBLE
                    binding.containerReconnect.visibility = View.GONE

                    binding.tilEnterPhoneNumber.prefixText = state.phoneMaskPrefix

                    binding.etEnterPhoneNumber.filters = arrayOf(LengthFilter(state.phoneMaskBody.length))
                    binding.etEnterPhoneNumber.hint = state.phoneMaskBody
                    if (binding.etEnterPhoneNumber.text.toString() != state.phoneInputBody) binding.etEnterPhoneNumber.setText(state.phoneInputBody)

                    if (binding.etEnterPassword.text.toString() != state.passwordInputBody) binding.etEnterPassword.setText(state.passwordInputBody)

                    if (state.passwordErrorMessage == null) binding.tvErrorTextFieldForPassword.visibility = View.GONE
                    else binding.tvErrorTextFieldForPassword.visibility = View.VISIBLE

                    if (state.passwordErrorMessage == null) binding.tilEnterPassword.boxStrokeColor = resources.getColor(com.sergokuzneczow.ui.R.color.gray, requireContext().theme)
                    else binding.tilEnterPassword.boxStrokeColor = resources.getColor(com.sergokuzneczow.ui.R.color.red, requireContext().theme)

                    binding.etEnterPhoneNumber.addTextChangedListener(object : TextWatcher {
                        private var cursorPositionBefore: Int = 0
                        private var lastTextLength: Int = 0
                        private var newTextLength: Int = 0

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                            cursorPositionBefore = binding.etEnterPhoneNumber.selectionStart
                            lastTextLength = s?.length ?: 0
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            newTextLength = s?.length ?: 0
                            s?.let { vm.dispatch(ChangePhoneTextField(it.toString())) }
                        }

                        override fun afterTextChanged(editable: Editable?) {
                            val newPosition = (cursorPositionBefore + (newTextLength - lastTextLength)).coerceAtMost(editable?.length ?: 0)
                            if (newPosition <= (editable?.length ?: 0)) {
                                binding.etEnterPhoneNumber.setSelection(newPosition)
                            }
                        }
                    })

                    binding.etEnterPassword.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(editable: Editable?) {
                            Selection.setSelection(editable, editable?.length ?: 0)
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            s?.let { vm.dispatch(ChangePasswordTextField(it.toString())) }
                        }
                    })

                    binding.btnLogIn.setOnClickListener { vm.dispatch(AuthorizationFragmentIntent.TryAuthenticate) }
                }

                AuthorizationFragmentState.ConnectionProblem -> {
                    binding.cpbLoading.visibility = View.GONE
                    binding.containerSuccess.visibility = View.GONE
                    binding.containerReconnect.visibility = View.VISIBLE
                    binding.btnReconnect.setOnClickListener { vm.dispatch(AuthorizationFragmentIntent.TryReconnect) }
                }
            }
        }.launchWhenLifecycleStateStarted()
    }

    private fun Flow<Any>.launchWhenLifecycleStateStarted() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@launchWhenLifecycleStateStarted.launchIn(this)
            }
        }
    }
}