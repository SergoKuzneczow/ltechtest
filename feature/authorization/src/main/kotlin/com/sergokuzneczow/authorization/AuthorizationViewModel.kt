package com.sergokuzneczow.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.authorization.AuthorizationFragmentIntent.ChangePasswordTextField
import com.sergokuzneczow.authorization.AuthorizationFragmentIntent.ChangePhoneTextField
import com.sergokuzneczow.authorization.AuthorizationFragmentIntent.TryAuthenticate
import com.sergokuzneczow.database.api.DatabaseDataSourceApi
import com.sergokuzneczow.domain.phone_mask_converter_case.CalculatePhoneMaskBodyCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.CalculatePhoneMaskPrefixCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.DeletePhoneMaskCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.PhoneMaskBodyConverterCaseApi
import com.sergokuzneczow.model.AuthResponse
import com.sergokuzneczow.model.AuthenticateRequest
import com.sergokuzneczow.navigator.NavigatorApi
import com.sergokuzneczow.network.api.NetworkDataSourceApi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
internal class AuthorizationViewModel @Inject constructor(
    val navigatorApi: NavigatorApi,
    private val networkDataSourceApi: NetworkDataSourceApi,
    private val databaseDataSourceApi: DatabaseDataSourceApi,
    private val calculatePhoneMaskPrefixCaseApi: CalculatePhoneMaskPrefixCaseApi,
    private val calculatePhoneMaskBodyCaseApi: CalculatePhoneMaskBodyCaseApi,
    private val phoneMaskBodyConverterCaseApi: PhoneMaskBodyConverterCaseApi,
    private val deletePhoneMaskCaseApi: DeletePhoneMaskCaseApi,
) : ViewModel(), ContainerHost<AuthorizationFragmentState, AuthorizationFragmentAction> {

    override val container: Container<AuthorizationFragmentState, AuthorizationFragmentAction> = viewModelScope.container(AuthorizationFragmentState.Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getPhoneMask()
        }
    }

    @OptIn(OrbitExperimental::class)
    fun dispatch(intent: AuthorizationFragmentIntent) {
        when (intent) {
            is ChangePhoneTextField -> intent {
                runOn<AuthorizationFragmentState.Success> {
                    reduce {
                        val phoneNumber = phoneMaskBodyConverterCaseApi.execute(state.phoneMaskBody, intent.field)
                        state.copy(phoneInputBody = phoneNumber)
                    }
                }
            }

            is ChangePasswordTextField -> intent {
                runOn<AuthorizationFragmentState.Success> {
                    if (state.passwordInputBody != intent.field) reduce { state.copy(passwordInputBody = intent.field, passwordErrorMessage = null) }
                }
            }

            is TryAuthenticate -> intent {
                runOn<AuthorizationFragmentState.Success> {
                    val authResponse: AuthResponse = networkDataSourceApi.getAuthResponse(
                        phone = deletePhoneMaskCaseApi.execute(state.phoneMaskPrefix, state.phoneInputBody),
                        password = state.passwordInputBody,
                    )
                    when (authResponse.success) {
                        true -> {
                            viewModelScope.launch(Dispatchers.IO) {
                                databaseDataSourceApi.setAuthenticateRequest(
                                    AuthenticateRequest(
                                        phoneMask = state.phoneMask,
                                        phoneMaskPrefix = state.phoneMaskPrefix,
                                        phoneMaskBody = state.phoneMaskBody,
                                        phoneBody = state.phoneInputBody,
                                        password = state.passwordInputBody,
                                    )
                                )
                            }
                            postSideEffect(AuthorizationFragmentAction.ToHome)
                        }

                        false -> {
                            when (authResponse.code) {
                                AuthResponse.AuthResponseCode.E200 -> reduce { state.copy(passwordErrorMessage = null) }
                                AuthResponse.AuthResponseCode.E400 -> reduce { state.copy(passwordErrorMessage = "Неверный пароль") }
                                AuthResponse.AuthResponseCode.E401 -> reduce { state.copy(passwordErrorMessage = "Неверный пароль") }
                                is AuthResponse.AuthResponseCode.Unknown -> reduce { state.copy(passwordErrorMessage = "Неверный пароль") }
                            }
                        }
                    }
                }

            }
        }
    }

    private suspend fun getPhoneMask() {
        runCatching {
            val mask = networkDataSourceApi.getPhoneMasks().phoneMask
            println(mask)
            mask
        }.onSuccess { mask ->
            val savedAuthenticateRequest: AuthenticateRequest? = databaseDataSourceApi.getAuthenticateRequest(mask)
            if (savedAuthenticateRequest != null) {
                intent {
                    reduce {
                        AuthorizationFragmentState.Success(
                            phoneMask = savedAuthenticateRequest.phoneMask,
                            phoneMaskPrefix = calculatePhoneMaskPrefixCaseApi.execute(mask),
                            phoneMaskBody = calculatePhoneMaskBodyCaseApi.execute(mask),
                            phoneInputBody = phoneMaskBodyConverterCaseApi.execute(savedAuthenticateRequest.phoneMaskBody, savedAuthenticateRequest.phoneBody),
                            passwordInputBody = savedAuthenticateRequest.password,
                        )
                    }
                }
            } else {
                intent {
                    reduce {
                        AuthorizationFragmentState.Success(
                            phoneMask = mask,
                            phoneMaskPrefix = calculatePhoneMaskPrefixCaseApi.execute(mask),
                            phoneMaskBody = calculatePhoneMaskBodyCaseApi.execute(mask),
                            phoneInputBody = "",
                            passwordInputBody = "",
                        )
                    }
                }
            }
        }.onFailure {
            delay(1.seconds)
            getPhoneMask()
        }
    }
}