package com.example.firebasenewsapplesson.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.model.getFullNameOrEmail
import com.example.firebasenewsapplesson.databinding.UserListItemBinding

class UserAdapter(
    private val context: Context,
    private val user: List<User>,
    val onClick: (user: User) -> Unit
) : RecyclerView.Adapter<UserAdapter.CustomViewHolder>() {

    class CustomViewHolder(binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvUserFullName = binding.tvUserFullName
        val ivUserImage =binding.ivUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            UserListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {
        val user = user[position]
        holder.tvUserFullName.text = user.getFullNameOrEmail()
        holder.ivUserImage.load(user.profileImageUrl){
            error(R.drawable.ic_launcher_foreground)
        }

        holder.itemView.setOnClickListener {
            onClick(user)
        }
    }

    override fun getItemCount(): Int {
        return user.size
    }
}