package com.example.firebasenewsapplesson.data.state

sealed class ProfilePhotoUpdateState {
    object Idle : ProfilePhotoUpdateState()
    object Loading : ProfilePhotoUpdateState()
    class Progress(val progress: Int) : ProfilePhotoUpdateState()
    object Success : ProfilePhotoUpdateState()
    class Error(val throwable: Throwable? = null) : ProfilePhotoUpdateState()
}