package com.sergokuzneczow.network.api

import com.sergokuzneczow.model.PhoneMask

public interface NetworkDataSourceApi {

    public suspend fun getPhoneMasks(): PhoneMask
}