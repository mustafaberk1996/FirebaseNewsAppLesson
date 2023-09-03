package com.example.firebasenewsapplesson.ui.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsapplesson.data.repository.AuthRepository
import com.example.firebasenewsapplesson.data.state.LoginState
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
 private val authRepository: AuthRepository
):ViewModel() {

    private val _loginState: MutableSharedFlow<LoginState> = MutableSharedFlow()
    val loginState: SharedFlow<LoginState> = _loginState

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message


    fun login(email: String, password: String) {
        viewModelScope.launch {
            runCatching {
                if (email.isNotEmpty() && password.isNotEmpty()){
                    _loginState.emit(LoginState.Loading)
                    _loginState.emit(authRepository.login(email, password))
                }else{
                    _message.emit("Do not leave fields empty")
                }
            }.onFailure {
                when(it){
                    is FirebaseAuthInvalidUserException->{
                        _loginState.emit(LoginState.UserNotFound)
                        _message.emit("user not found")
                    }else->{
                        _loginState.emit(LoginState.Error(it))
                    }
                }
            }

        }
    }



}