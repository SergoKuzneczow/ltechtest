package com.sergokuzneczow.network.api

import com.sergokuzneczow.model.AuthResponse
import com.sergokuzneczow.model.PhoneMask

public interface NetworkDataSourceApi {

    public suspend fun getPhoneMasks(): PhoneMask

    public suspend fun getAuthResponse(phone: String, password: String): AuthResponse
}