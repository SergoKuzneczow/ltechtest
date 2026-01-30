package com.sergokuzneczow.network.impl

import com.sergokuzneczow.model.PhoneMask
import com.sergokuzneczow.network.api.NetworkDataSourceApi
import com.sergokuzneczow.network.impl.models.asPhoneMask
import com.sergokuzneczow.network.impl.retrofit.PhoneMaskApi
import com.sergokuzneczow.network.impl.retrofit.RetrofitHandler
import jakarta.inject.Inject

public class NetworkDataSourceImpl private constructor(
    private val phoneMaskApi: PhoneMaskApi,
) : NetworkDataSourceApi {

    @Inject
    internal constructor(retrofitHandler: RetrofitHandler) : this(phoneMaskApi = retrofitHandler.phoneMaskApi)

    override suspend fun getPhoneMasks(): PhoneMask = phoneMaskApi.getPhoneMask().asPhoneMask
}