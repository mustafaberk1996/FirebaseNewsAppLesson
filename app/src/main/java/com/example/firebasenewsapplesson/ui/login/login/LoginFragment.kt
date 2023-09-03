package com.example.firebasenewsapplesson.ui.login.login

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
import com.example.firebasenewsapplesson.data.state.LoginState
import com.example.firebasenewsapplesson.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel:LoginViewModel by activityViewModels()

    private lateinit var binding:FragmentLoginBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        initListeners()

        observeLoginState()
        observeMessage()

    }

    private fun observeMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.message.collect{
                    Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.loginState.collect{
                  stateForViews(it is LoginState.Loading)
                    when(it){
                        is LoginState.Idle->{}
                        is LoginState.Loading->{}
                        is LoginState.UserNotFound->{
                            Snackbar.make(binding.btnLogin,"User not found",Snackbar.LENGTH_LONG).setAction("SignUp"){
                                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
                            }.show()
                        }
                        is LoginState.Success->{
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                        }
                        is LoginState.Error->{
                            Snackbar.make(binding.btnLogin,"Something went wrong",Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun stateForViews(isLoading:Boolean){
        binding.progressBar.isVisible = isLoading
        binding.btnLogin.isVisible = !isLoading
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading
    }

    private fun initListeners() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }


        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }
    }

}