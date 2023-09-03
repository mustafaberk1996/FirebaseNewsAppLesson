package com.example.firebasenewsapplesson.data.model

import com.google.firebase.Timestamp

data class User(
    val id:String?=null,
    val email:String?=null,
    val registeredTimestamp: Timestamp = Timestamp.now(),
    val name:String?=null,
    val surname:String?=null,
    val profileImageUrl:String?=null
)

fun User.getFullNameOrEmail() =
    if (!name.isNullOrEmpty() && !surname.isNullOrEmpty())
        "$name $surname" else email
