package com.example.firebasenewsapplesson.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.firebasenewsapplesson.Constants.EDITOR_ID
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.state.NewsListState
import com.example.firebasenewsapplesson.databinding.FragmentNewsBinding
import kotlinx.coroutines.launch


class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)


        val editorId = arguments?.getString(EDITOR_ID)
        viewModel.getNews(editorId)

        observeNewsListState()


    }

    private fun observeNewsListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.newsListState.collect{
                    when(it){
                        is NewsListState.Idle->{}
                        is NewsListState.Result->{
                          binding.rvNews.adapter = NewsAdapter(requireContext(), it.news)
                        }
                        is NewsListState.Loading->{}
                        is NewsListState.Error->{}
                    }
                }
            }
        }
    }
}