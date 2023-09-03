package com.example.firebasenewsapplesson.data.repository

import com.example.firebasenewsapplesson.Constants.USERS
import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.state.UserListState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : UserRepository{
    override suspend fun insert(user: User) {
        firestore.collection(USERS).document(user.id.toString()).set(user)
    }

    override suspend fun getSignedUser(): User? {
      return firestore.collection(USERS).document(firebaseAuth.currentUser?.uid.orEmpty()).get().await().toObject(User::class.java)
    }

    override suspend fun getAllUsers(): UserListState {
        val querySnapshot = firestore.collection(USERS).get().await()
        return if (querySnapshot.isEmpty) UserListState.Empty
        else UserListState.Result(querySnapshot.toObjects(User::class.java))
    }
}