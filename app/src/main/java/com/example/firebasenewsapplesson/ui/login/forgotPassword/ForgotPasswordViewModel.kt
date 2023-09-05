package com.example.firebasenewsapplesson.ui.login.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsapplesson.data.repository.UserRepository
import com.example.firebasenewsapplesson.data.state.ResetPasswordState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
):ViewModel(){



    private val _resetPasswordState:MutableStateFlow<ResetPasswordState> = MutableStateFlow(ResetPasswordState.Idle)
    val resetPasswordState:StateFlow<ResetPasswordState> = _resetPasswordState


    fun sendResetPasswordLink(emailAddress:String) {
        viewModelScope.launch {
            runCatching {
                _resetPasswordState.emit(ResetPasswordState.Loading)
                userRepository.sendPasswordResetEmail(emailAddress)
                _resetPasswordState.emit(ResetPasswordState.Success)
            }.onFailure {
              _resetPasswordState.emit(ResetPasswordState.Error(it))
            }
        }
    }
}
