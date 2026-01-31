package com.sergokuzneczow.database.api

import com.sergokuzneczow.model.AuthenticateRequest

public interface DatabaseDataSourceApi {

    public suspend fun setAuthenticateRequest(authenticateRequest: AuthenticateRequest)

    public suspend fun getAuthenticateRequest(phoneMask: String): AuthenticateRequest?
}