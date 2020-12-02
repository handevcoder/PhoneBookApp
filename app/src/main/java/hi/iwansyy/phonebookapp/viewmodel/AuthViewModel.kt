package hi.iwansyy.phonebookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hi.iwansyy.phonebookapp.model.AuthModel
import hi.iwansyy.phonebookapp.repository.remote.client.AuthClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


sealed class AuthState{
    data class Loading(val message: String = "Loading..") : AuthState()
    data class Error(val exception: Exception) : AuthState()
    data class Login(val data: AuthModel) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val authService by lazy { AuthClient.service }
    private val mutableState by lazy { MutableLiveData<AuthState>() }
    val state: LiveData<AuthState> get() = mutableState

    fun login(email: String, password: String){
        val body = hashMapOf<String, Any>("email" to email, "password" to password)
        mutableState.value = AuthState.Loading()
        viewModelScope.launch(Dispatchers.IO){
            try {
                val authModel = authService.login(body).data
                mutableState.postValue(AuthState.Login(authModel))
            } catch (exc: Exception) {
                exc.printStackTrace()
                mutableState.postValue(AuthState.Error(exc))
            }
        }
    }
}