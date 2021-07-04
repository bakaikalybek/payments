package kg.bakai.payments.data.network

import kg.bakai.payments.data.model.AuthResponse
import kg.bakai.payments.data.model.PaymentResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("login")
    fun login(
        @Body body: RequestBody
    ): Call<AuthResponse>

    @GET("payments")
    fun getPayments(
        @Query("token") token: String
    ): Call<PaymentResponse>
}