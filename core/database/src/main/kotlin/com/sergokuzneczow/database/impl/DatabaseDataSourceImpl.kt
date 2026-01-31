package com.sergokuzneczow.database.impl

import com.sergokuzneczow.database.api.DatabaseDataSourceApi
import com.sergokuzneczow.database.impl.room.dao.AuthenticateRequestDao
import com.sergokuzneczow.database.impl.room.database.LtechDatabaseImpl
import com.sergokuzneczow.database.impl.room.entity.asAuthenticateRequest
import com.sergokuzneczow.database.impl.room.entity.asAuthenticateRequestLocalModel
import com.sergokuzneczow.model.AuthenticateRequest
import jakarta.inject.Inject

public class DatabaseDataSourceImpl private constructor(
    private val authenticateRequestDao: AuthenticateRequestDao
) : DatabaseDataSourceApi {

    @Inject
    internal constructor(database: LtechDatabaseImpl) : this(database.database.getAuthenticateRequestDao())

    override suspend fun setAuthenticateRequest(authenticateRequest: AuthenticateRequest) {
        authenticateRequestDao.insertOrReplace(authenticateRequest.asAuthenticateRequestLocalModel)
    }

    override suspend fun getAuthenticateRequest(phoneMask: String): AuthenticateRequest? = authenticateRequestDao.queryLastAuthenticateRequestColumn(phoneMask)?.asAuthenticateRequest
}