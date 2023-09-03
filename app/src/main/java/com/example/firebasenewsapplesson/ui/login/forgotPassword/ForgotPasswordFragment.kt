package com.example.firebasenewsapplesson.ui.login.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {


    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgotPasswordBinding.bind(view)


        initListeners()
    }

    private fun initListeners() {
        binding.tvRememberMyPassword.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
    }
}