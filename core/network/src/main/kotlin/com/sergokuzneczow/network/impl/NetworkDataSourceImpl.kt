package com.sergokuzneczow.network.impl

import com.sergokuzneczow.model.AuthResponse
import com.sergokuzneczow.model.PhoneMask
import com.sergokuzneczow.network.api.NetworkDataSourceApi
import com.sergokuzneczow.network.impl.models.AuthResponseRemoteModel
import com.sergokuzneczow.network.impl.models.asPhoneMask
import com.sergokuzneczow.network.impl.retrofit.RetrofitHandler
import com.sergokuzneczow.network.impl.retrofit.api.AuthResponseApi
import com.sergokuzneczow.network.impl.retrofit.api.PhoneMaskApi
import jakarta.inject.Inject
import retrofit2.HttpException
import retrofit2.Response

public class NetworkDataSourceImpl private constructor(
    private val phoneMaskApi: PhoneMaskApi,
    private val authResponseApi: AuthResponseApi,
) : NetworkDataSourceApi {

    @Inject
    internal constructor(retrofitHandler: RetrofitHandler) : this(retrofitHandler.phoneMaskApi, retrofitHandler.authResponseApi)

    override suspend fun getPhoneMasks(): PhoneMask = phoneMaskApi.getPhoneMask().asPhoneMask

    override suspend fun getAuthResponse(phone: String, password: String): AuthResponse {
        return try {
            val response: Response<AuthResponseRemoteModel> = authResponseApi.authenticate(phone, password)

            when {
                response.isSuccessful && response.body()?.success == true -> {
                    AuthResponse(
                        success = true,
                        code = AuthResponse.AuthResponseCode.E200,
                    )
                }

                response.code() == 400 -> {
                    AuthResponse(
                        success = false,
                        code = AuthResponse.AuthResponseCode.E400,
                    )
                }

                response.code() == 401 -> {
                    AuthResponse(
                        success = false,
                        code = AuthResponse.AuthResponseCode.E401,
                    )
                }

                else -> {
                    AuthResponse(
                        success = false,
                        code = AuthResponse.AuthResponseCode.Unknown("Unknown response state case."),
                    )
                }
            }
        } catch (e: HttpException) {
            AuthResponse(
                success = false,
                code = AuthResponse.AuthResponseCode.Unknown(e.message.toString()),
            )
        } catch (e: Exception) {
            AuthResponse(
                success = false,
                code = AuthResponse.AuthResponseCode.Unknown(e.message.toString()),
            )
        }
    }
}