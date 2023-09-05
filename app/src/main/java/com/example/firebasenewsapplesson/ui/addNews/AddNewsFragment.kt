package com.example.firebasenewsapplesson.ui.addNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.firebasenewsapplesson.Constants
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.databinding.FragmentAddNewsBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class AddNewsFragment : Fragment(R.layout.fragment_add_news) {


    private lateinit var binding:FragmentAddNewsBinding
    private val viewModel:AddNewsViewModel by activityViewModels()

    // Registers a photo picker activity launcher in single-select mode.
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.

        viewModel.setNewsPhotos(uris)

        uris.forEach {
            val imageView = ImageView(requireContext())
            imageView.setImageURI(it)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,300)
            binding.llImages.addView(imageView)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddNewsBinding.bind(view)

        binding.ivAddImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.ivAddImage123.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()
            viewModel.addNews(title, content)
        }
    }
}