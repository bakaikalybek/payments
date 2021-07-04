package kg.bakai.payments.data.model

data class AuthResponse(
    val response: Token,
    val success: String
)

data class Token(
    val token: String
)