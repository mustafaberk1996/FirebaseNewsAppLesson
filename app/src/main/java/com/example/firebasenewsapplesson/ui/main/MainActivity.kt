package com.example.firebasenewsapplesson.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.firebasenewsapplesson.Constants.NEWS
import com.example.firebasenewsapplesson.R
import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.databinding.ActivityMainBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navHost  = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        val appBarConfiguration=  AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController,appBarConfiguration)


    }
}