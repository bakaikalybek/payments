package kg.bakai.payments.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.bakai.payments.data.model.Payment
import kg.bakai.payments.data.model.Resource
import kg.bakai.payments.data.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MainRepository
): ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _paymentsResponse = MutableLiveData<Resource<List<Payment>>>()
    val paymentsResponse: LiveData<Resource<List<Payment>>> = _paymentsResponse

    fun getPayments() {
        scope.launch {
            _paymentsResponse.postValue(Resource.loading(null))
            val data = repository.getPayments()
            _paymentsResponse.postValue(data)
        }
    }

    fun logOut() {
        repository.removeToken()
    }

}