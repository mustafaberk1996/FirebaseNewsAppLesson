package com.example.firebasenewsapplesson.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.data.model.getFullNameOrEmail
import com.example.firebasenewsapplesson.databinding.NewsListItemBinding
import com.example.firebasenewsapplesson.databinding.UserListItemBinding

class NewsAdapter(
    private val context: Context,
    private val newsAdapterModels: List<NewsAdapterModel>,
    private val onEditClick:(news:News)->Unit
) : RecyclerView.Adapter<NewsAdapter.CustomViewHolder>() {

    class CustomViewHolder(binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitle
        val tvContent = binding.tvContent
        val ivEdit = binding.ivEdit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            NewsListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {
        val newsAdapterModel = newsAdapterModels[position]
        holder.tvTitle.text = newsAdapterModel.news.title
        holder.tvContent.text = newsAdapterModel.news.content

        holder.ivEdit.setOnClickListener {
            onEditClick(newsAdapterModel.news)
        }

    }

    override fun getItemCount(): Int {
        return newsAdapterModels.size
    }
}