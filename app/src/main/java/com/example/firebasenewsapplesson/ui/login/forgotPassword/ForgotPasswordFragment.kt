package com.example.firebasenewsapplesson.ui.login.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.state.ResetPasswordState
import com.example.firebasenewsapplesson.databinding.FragmentForgotPasswordBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {


    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel:ForgotPasswordViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgotPasswordBinding.bind(view)

        initListeners()

        observeResetPasswordState()
    }

    private fun observeResetPasswordState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.resetPasswordState.collect{
                    when(it){
                        is ResetPasswordState.Idle->{}
                        is ResetPasswordState.Loading->{
                            binding.progressBar.isVisible = true
                            binding.btnResetMyPassword.isVisible = false
                        }
                        is ResetPasswordState.Success->{

                        }
                        is ResetPasswordState.Error->{

                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.tvRememberMyPassword.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }

        binding.btnResetMyPassword.setOnClickListener {
            viewModel.sendResetPasswordLink(binding.etEmail.text.trim().toString())
        }
    }
}