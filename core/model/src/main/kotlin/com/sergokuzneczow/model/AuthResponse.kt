package com.sergokuzneczow.model

public data class AuthResponse(
    val success: Boolean,
    val code: AuthResponseCode,
) {

    public sealed interface AuthResponseCode {
        public val message: String

        public data object E200 : AuthResponseCode {
            override val message: String = "Success"
        }

        public data object E400 : AuthResponseCode {
            override val message: String = "400 Bad request"
        }

        public data object E401 : AuthResponseCode {
            override val message: String = "401 Unauthorized"
        }

        public data class Unknown(override val message: String) : AuthResponseCode
    }
}