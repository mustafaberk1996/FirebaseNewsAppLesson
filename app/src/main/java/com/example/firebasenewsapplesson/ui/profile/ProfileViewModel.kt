package com.example.firebasenewsapplesson.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsapplesson.Constants.NAME
import com.example.firebasenewsapplesson.Constants.SURNAME
import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.repository.UserRepository
import com.example.firebasenewsapplesson.data.state.ProfilePhotoUpdateState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth,
    private val _profilePhotoUpdateState: MutableSharedFlow<ProfilePhotoUpdateState>
):ViewModel() {


    val profilePhotoUpdateState = _profilePhotoUpdateState.asSharedFlow()

    private val _signedUser:MutableStateFlow<User?> = MutableStateFlow(null)
    val signedUser:StateFlow<User?> = _signedUser

    fun getUser(){
        viewModelScope.launch {
            _signedUser.emit(userRepository.getSignedUser())
        }
    }
    fun updateUser(){
    }

    fun updateProfileImage(){
    }
    fun uploadProfileImage(uri: Uri) {
        viewModelScope.launch {
            userRepository.uploadProfileImage(uri)
        }
    }
    fun updateProfile(name: String, surname: String) {
        viewModelScope.launch {
            val map = mapOf(
                NAME to name,
                SURNAME to surname
            )
            val state = userRepository.updateProfile(map)
        }
    }


}