package kg.bakai.payments.data.model

data class PaymentResponse(
    val response: List<Payment>,
    val success: String
)

data class Payment(
    val amount: Any?,
    val created: Int?,
    val currency: String?,
    val desc: String?
)