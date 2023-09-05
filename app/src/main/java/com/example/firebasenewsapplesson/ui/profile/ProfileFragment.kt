package com.example.firebasenewsapplesson.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding:FragmentProfileBinding
    private val viewModel:ProfileViewModel by activityViewModels()

    // Registers a photo picker activity launcher in single-select mode.
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            binding.ivUser.load(uri)
            viewModel.uploadProfileImage(uri)
            //Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            //Log.d("PhotoPicker", "No media selected")
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)


        viewModel.getUser()


        initListeners()

        observeSignedUser()




    }

    private fun initListeners() {
        binding.ivUser.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnSave.setOnClickListener {
            viewModel.updateProfile(binding.etName.text.toString().trim(), binding.etSurName.text.toString().trim())
        }
    }

    private fun observeSignedUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.signedUser.collect{
                    it?.let {
                        binding.ivUser.load(it.profileImageUrl)
                        binding.etName.setText(it.name)
                        binding.etSurName.setText(it.surname)
                    }

                }
            }
        }
    }
}