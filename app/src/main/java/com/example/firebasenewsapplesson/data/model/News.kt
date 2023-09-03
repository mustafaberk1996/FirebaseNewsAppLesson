package com.example.firebasenewsapplesson.data.model

import com.google.firebase.Timestamp

data class News(
    val id:String? = null,
    val title:String? = null,
    val content:String? = null,
    val editorId:String? = null,
    val createdAt:Timestamp = Timestamp.now(),
    val updatedAt:Timestamp? = null
)
