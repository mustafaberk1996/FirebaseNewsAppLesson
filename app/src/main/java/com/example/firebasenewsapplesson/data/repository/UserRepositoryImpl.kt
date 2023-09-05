package com.example.firebasenewsapplesson.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.example.firebasenewsapplesson.Constants.PROFILE_IMAGE_URL
import com.example.firebasenewsapplesson.Constants.USERS
import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.state.UserListState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage
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

    override suspend fun uploadProfileImage(uri: Uri) {


        val reference = firebaseStorage.reference.child(firebaseAuth.currentUser?.uid.toString())
        val uploadTask = reference.putFile(uri)

        uploadTask.addOnCompleteListener {task->
            if (task.isSuccessful) {
                CoroutineScope(Dispatchers.IO).launch {
                    val downloadUri = task.result.storage.downloadUrl.await()
                    updateUserProfileImage(downloadUri.toString())
                }
            } else {
                //..
            }

        }.addOnProgressListener {taskSnapshot->
            val pr = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
            //profilePhotoUpdateState.update(ProfilePhotoUpdateState.Progress(pr.toInt()))
        }.addOnFailureListener {
            it.printStackTrace()
        }

    }

    override suspend fun updateProfile(map: Map<String, String>) {
        firestore.collection(USERS).document(firebaseAuth.currentUser?.uid.toString()).update(map).await()
    }

    override suspend fun sendPasswordResetEmail(emailAddress: String) {
        firebaseAuth.sendPasswordResetEmail(emailAddress).await()
    }

    private suspend fun updateUserProfileImage(downloadPath: String) {
        firestore.collection(USERS).document(firebaseAuth.currentUser?.uid.toString()).update(mapOf(PROFILE_IMAGE_URL to downloadPath)).await()
    }
}