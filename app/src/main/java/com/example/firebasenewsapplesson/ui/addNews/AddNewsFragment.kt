package com.example.firebasenewsapplesson.ui.addNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.firebasenewsapplesson.Constants
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.databinding.FragmentAddNewsBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class AddNewsFragment : Fragment(R.layout.fragment_add_news) {


    private lateinit var binding:FragmentAddNewsBinding
    private val viewModel:AddNewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddNewsBinding.bind(view)


        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()
            viewModel.addNews(title, content)
        }
    }
}