package com.example.firebasenewsapplesson.ui.addNews

import android.icu.text.CaseMap.Title
import android.util.Printer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.data.repository.NewsRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val firebaseAuth: FirebaseAuth
) :ViewModel() {



    fun addNews(title: String, content:String){
        viewModelScope.launch {
            val news = News(title = title, content = content, editorId = firebaseAuth.currentUser?.uid)
            newsRepository.addNews(news)
        }
    }



}