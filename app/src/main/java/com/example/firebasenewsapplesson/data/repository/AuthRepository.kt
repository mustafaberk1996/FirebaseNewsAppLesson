package com.example.firebasenewsapplesson.data.repository

import com.example.firebasenewsapplesson.data.state.LoginState
import com.google.firebase.auth.AuthResult

interface AuthRepository {

    suspend fun login(email:String, password:String):LoginState

    suspend fun register(email: String, password: String): AuthResult

}