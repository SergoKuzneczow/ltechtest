package com.sergokuzneczow.authorization

internal sealed interface AuthorizationFragmentState {
    data object Loading : AuthorizationFragmentState
    data class Success(
        val phoneMask: String,
        val phoneMaskPrefix: String,
        val phoneMaskBody: String,
        val phoneInputBody: String,
        val passwordInputBody: String,
        val passwordErrorMessage: String? = null,
    ) : AuthorizationFragmentState

    data object ConnectionProblem : AuthorizationFragmentState
}

internal sealed interface AuthorizationFragmentAction {
    data object ToHome : AuthorizationFragmentAction
}

internal sealed interface AuthorizationFragmentIntent {
    data class ChangePhoneTextField(val field: String) : AuthorizationFragmentIntent
    data class ChangePasswordTextField(val field: String) : AuthorizationFragmentIntent
    data object TryAuthenticate : AuthorizationFragmentIntent
    data object TryReconnect : AuthorizationFragmentIntent
    data object ConnectionProblem : AuthorizationFragmentIntent
}