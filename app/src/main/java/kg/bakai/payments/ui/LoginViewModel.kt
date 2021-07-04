package kg.bakai.payments.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.bakai.payments.data.model.AuthResponse
import kg.bakai.payments.data.model.Payment
import kg.bakai.payments.data.model.Resource
import kg.bakai.payments.data.repository.MainRepository
import kg.bakai.payments.util.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: MainRepository
): ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _loginResponse = MutableLiveData<Resource<AuthResponse>>()
    val loginResponse: LiveData<Resource<AuthResponse>> = _loginResponse

    fun login(login: String, password: String) {
        scope.launch {
            _loginResponse.postValue(Resource.loading(null))
            _loginResponse.postValue(repository.login(login, password))
        }
    }

    fun checkSession(): Boolean {
        return repository.checkSession()
    }


}