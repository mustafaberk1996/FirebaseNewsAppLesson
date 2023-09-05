package com.example.firebasenewsapplesson.data.repository

import android.net.Uri
import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.state.UserListState

interface UserRepository {

    suspend fun insert(user:User)
    suspend fun getSignedUser(): User?
    suspend fun getAllUsers(): UserListState
    suspend fun uploadProfileImage(uri: Uri)
    suspend fun updateProfile(map: Map<String, String>)
    suspend fun sendPasswordResetEmail(emailAddress: String)
}