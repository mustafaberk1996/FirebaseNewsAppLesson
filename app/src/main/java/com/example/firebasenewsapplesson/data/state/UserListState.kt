package com.example.firebasenewsapplesson.data.state

import com.example.firebasenewsapplesson.data.model.User

sealed class UserListState{
    object Idle:UserListState()
    object Loading:UserListState()
    object Empty:UserListState()
    class Result(val users:List<User>):UserListState()
    class Error(val throwable: Throwable? = null):UserListState()
}
