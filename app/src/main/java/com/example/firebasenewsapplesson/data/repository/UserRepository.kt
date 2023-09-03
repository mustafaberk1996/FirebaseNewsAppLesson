package com.example.firebasenewsapplesson.data.repository

import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.state.UserListState

interface UserRepository {

    suspend fun insert(user:User)
    suspend fun getSignedUser(): User?
    suspend fun getAllUsers(): UserListState
}