package com.example.firebasenewsapplesson.ui.login.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.state.RegisterState
import com.example.firebasenewsapplesson.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding:FragmentSignUpBinding
    private val viewModel:SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        initListeners()

        observeRegisterState()
        observeMessage()

    }

    private fun observeMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.message.collect{
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun observeRegisterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.registerState.collect{
                    stateForViews(it is RegisterState.Loading)
                    when(it){
                        is RegisterState.Idle->{}
                        is RegisterState.Loading->{}
                        is RegisterState.Success->{
                            Toast.makeText(requireContext(),"register is successfully",Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_signUpFragment_to_dashboardFragment)
                        }
                        is RegisterState.Error->{
                            Snackbar.make(binding.btnSignUp,"Something went wrong: ${it.throwable?.message}",Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun stateForViews(isLoading:Boolean){
        binding.progressBar.isVisible = isLoading
        binding.btnSignUp.isEnabled = !isLoading
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading
        binding.etPasswordAgain.isEnabled = !isLoading
    }

    private fun initListeners() {
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.btnSignUp.setOnClickListener {
            viewModel.register(
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim(),
                binding.etPasswordAgain.text.toString().trim()
            )
        }
    }
}