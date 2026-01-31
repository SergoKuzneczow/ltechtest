package com.sergokuzneczow.model

public data class AuthenticateRequest(
    val phoneMask: String,
    val phoneMaskPrefix: String,
    val phoneMaskBody: String,
    val phoneBody: String,
    val password: String,
)