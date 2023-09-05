package com.example.firebasenewsapplesson.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.repository.NewsRepository
import com.example.firebasenewsapplesson.data.repository.UserRepository
import com.example.firebasenewsapplesson.data.state.NewsDataState
import com.example.firebasenewsapplesson.data.state.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val newsRepository: NewsRepository,
    private val mutableNewsDataState: MutableStateFlow<NewsDataState>
):ViewModel() {


    val newsDataState = mutableNewsDataState.asStateFlow()

    private val _user:MutableSharedFlow<User> = MutableSharedFlow()
    val user:SharedFlow<User> = _user


    private val _userListState:MutableStateFlow<UserListState> = MutableStateFlow(UserListState.Idle)
    val userListState:StateFlow<UserListState> = _userListState

    init {


        viewModelScope.launch {
            newsRepository.listenNews()
            userRepository.getSignedUser()?.let {
                _user.emit(it)
            }?: kotlin.run {
                //go login....
            }
        }
    }


    fun getUsers() {
        viewModelScope.launch {
            runCatching {
                _userListState.value = UserListState.Loading
                _userListState.value = userRepository.getAllUsers()
            }.onFailure {
                _userListState.value = UserListState.Error(it)
            }
        }
    }


}