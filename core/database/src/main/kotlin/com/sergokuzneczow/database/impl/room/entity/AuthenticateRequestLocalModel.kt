package com.sergokuzneczow.database.impl.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sergokuzneczow.model.AuthenticateRequest

@Entity(
    tableName = "authenticate_requests",
)
internal data class AuthenticateRequestLocalModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "authenticate_requests_key") val key: Int? = null,
    @ColumnInfo(name = "phone_mask") val phoneMask: String,
    @ColumnInfo(name = "phone_mask_prefix") val phoneMaskPrefix: String,
    @ColumnInfo(name = "phone_mask_body") val phoneMaskBody: String,
    @ColumnInfo(name = "phone_body") val phoneBody: String,
    @ColumnInfo(name = "password") val password: String,
)

internal val AuthenticateRequest.asAuthenticateRequestLocalModel: AuthenticateRequestLocalModel
    get() = AuthenticateRequestLocalModel(
        phoneMask = this.phoneMask,
        phoneMaskPrefix = this.phoneMaskPrefix,
        phoneMaskBody = this.phoneMaskBody,
        phoneBody = this.phoneBody,
        password = this.password,
    )

internal val AuthenticateRequestLocalModel.asAuthenticateRequest: AuthenticateRequest
    get() = AuthenticateRequest(
        phoneMask = this.phoneMask,
        phoneMaskPrefix = this.phoneMaskPrefix,
        phoneMaskBody = this.phoneMaskBody,
        phoneBody = this.phoneBody,
        password = this.password,
    )