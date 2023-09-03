package com.example.firebasenewsapplesson.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.model.User
import com.example.firebasenewsapplesson.data.model.getFullNameOrEmail
import com.example.firebasenewsapplesson.data.state.UserListState
import com.example.firebasenewsapplesson.databinding.FragmentDashboardBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding:FragmentDashboardBinding
    private val viewModel:DashboardViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)


        viewModel.getUsers()

        initListeners()

        observeUser()
        observeUserListState()

    }

    private fun initListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getUsers()
        }

        binding.btnAddNews.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addNewsFragment)
        }
    }

    private fun observeUserListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.userListState.collect {
                    binding.swipeRefresh.isRefreshing = false
                    println("gelen userliststate: $it")
                    when(it){
                        is UserListState.Idle->{}
                        is UserListState.Loading->{
                            binding.progressBar.isVisible = true
                            binding.rvUsers.isVisible = false
                            binding.ivEmpty.isVisible = false
                        }
                        is UserListState.Empty->{
                            binding.ivEmpty.setImageResource(R.drawable.empty)
                            binding.ivEmpty.isVisible = true
                            binding.progressBar.isVisible = false
                            binding.rvUsers.isVisible = false
                        }
                        is UserListState.Result->{
                            binding.ivEmpty.isVisible = false
                            binding.progressBar.isVisible = false
                            binding.rvUsers.isVisible = true

                            binding.rvUsers.adapter = UserAdapter(requireContext(), it.users, this@DashboardFragment::onUserAdapterListItemClick)
                            ///adapter...
                        }
                        is UserListState.Error->{
                            binding.ivEmpty.setImageResource(R.drawable.error)
                            binding.ivEmpty.isVisible = true
                            binding.progressBar.isVisible = false
                            binding.rvUsers.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun onUserAdapterListItemClick(user: User){

    }



    private fun observeUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.user.collect{

                }
            }
        }
    }
}