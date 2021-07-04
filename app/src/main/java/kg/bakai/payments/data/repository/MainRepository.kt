package kg.bakai.payments.data.repository

import kg.bakai.payments.data.model.AuthResponse
import kg.bakai.payments.data.model.Payment
import kg.bakai.payments.data.model.Resource
import kg.bakai.payments.data.network.ApiService
import kg.bakai.payments.util.SessionManager
import kg.bakai.payments.util.Result
import kz.laccent.util.awaitResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MainRepository(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
    ) {

    fun checkSession(): Boolean {
        return !sessionManager.fetchAuthToken().isNullOrEmpty()
    }

    fun removeToken() {
        sessionManager.removeAuthToken()
    }

    suspend fun login(login: String, password: String): Resource<AuthResponse> {
        val content = JSONObject()
        content.put("login", login)
        content.put("password", password)

        val requestBody = content.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return when (val response = apiService.login(requestBody).awaitResult()) {
            is Result.Ok -> {
                val data = response.value
                if (data.success == "true") {
                    sessionManager.saveAuthToken(data.response.token)
                    Resource.success(data)
                } else {
                    Resource.error(null, "Incorrect credentials")
                }
            }
            is Result.Error -> {
                Resource.error(null, "Error")
            }
            is Result.Exception -> {
                val data = response.exception
                Resource.error(null, data.localizedMessage)
            }
        }
    }

    suspend fun getPayments(): Resource<List<Payment>> {
        return when (val response = apiService.getPayments(sessionManager.fetchAuthToken()!!).awaitResult()) {
            is Result.Ok -> {
                val data = response.value.response
                Resource.success(data)
            }
            is Result.Error -> {
                Resource.error(null, "Error getting payments")
            }
            is Result.Exception -> {
                val data = response.exception
                Resource.error(null, "$data")
            }
        }
    }
}