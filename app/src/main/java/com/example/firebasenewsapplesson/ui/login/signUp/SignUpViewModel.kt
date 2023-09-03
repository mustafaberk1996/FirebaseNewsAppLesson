package com.example.firebasenewsapplesson.ui.login.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.repository.AuthRepository
import com.example.firebasenewsapplesson.data.repository.UserRepository
import com.example.firebasenewsapplesson.data.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message


    private val _registerState: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState


    fun register(email: String, password: String, passwordAgain: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                if (email.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()) {
                    if (password == passwordAgain) {
                        //register ol, repository
                        _registerState.value = RegisterState.Loading
                        authRepository.register(email, password).user?.let {
                            val user = User(it.uid,it.email)
                            userRepository.insert(user)
                        }
                        _registerState.value = RegisterState.Success
                    } else {
                        //message
                        _message.emit("Passwords are not matched!")
                    }
                } else {
                    //message
                    _message.emit("Do not leave fields empty")
                }
            }.onFailure {
                _registerState.value = RegisterState.Error(it)
            }
        }

    }


}