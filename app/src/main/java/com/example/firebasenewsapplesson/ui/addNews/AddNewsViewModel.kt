package com.example.firebasenewsapplesson.ui.addNews

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.data.repository.NewsRepository
import com.example.firebasenewsapplesson.data.state.AddNewsState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val firebaseAuth: FirebaseAuth
) :ViewModel() {

    private val _newsPhotos:MutableStateFlow<List<Uri>> = MutableStateFlow(emptyList())
    //val newsPhotos:SharedFlow<List<Uri>> = _newsPhotos

    private val _addNewsState:MutableSharedFlow<AddNewsState> = MutableSharedFlow()
    val addNewsState: SharedFlow<AddNewsState> = _addNewsState

    fun addNews(title: String, content:String){
        viewModelScope.launch {
            kotlin.runCatching {
                _addNewsState.emit(AddNewsState.Loading)
                val news =
                    News(title = title, content = content, editorId = firebaseAuth.currentUser?.uid)
                val photos = _newsPhotos.value
                newsRepository.addNews(news, photos)
                _addNewsState.emit(AddNewsState.Success)
            }.onFailure {
                _addNewsState.emit(AddNewsState.Error(it))
            }
        }
    }

    fun setNewsPhotos(uris: List<Uri>) {
        viewModelScope.launch {
            _newsPhotos.emit(uris)
        }
    }


}