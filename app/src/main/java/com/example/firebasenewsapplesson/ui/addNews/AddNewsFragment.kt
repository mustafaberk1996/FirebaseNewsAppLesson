package com.example.firebasenewsapplesson.ui.addNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.firebasenewsapplesson.Constants
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.data.state.AddNewsState
import com.example.firebasenewsapplesson.databinding.FragmentAddNewsBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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


        observeAddNewsState()

        binding.ivAddImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()
            viewModel.addNews(title, content)
        }
    }

    private fun observeAddNewsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.addNewsState.collect{
                    when(it){
                        is AddNewsState.Idle->{}
                        is AddNewsState.Loading->{
                            Toast.makeText(requireContext(), "Loading",Toast.LENGTH_LONG).show()
                        }
                        is AddNewsState.Success->{
                            binding.etTitle.text.clear()
                            binding.etContent.text.clear()
                            binding.llImages.removeAllViews()
                            Toast.makeText(requireContext(), "success",Toast.LENGTH_LONG).show()
                        }
                        is AddNewsState.Error->{
                            println(it.throwable?.printStackTrace())
                            Toast.makeText(requireContext(), "error",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}