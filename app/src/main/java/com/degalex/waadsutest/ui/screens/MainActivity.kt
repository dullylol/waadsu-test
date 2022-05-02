package com.degalex.waadsutest.ui.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.degalex.waadsutest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.onAttachActivity(this)

        if (savedInstanceState == null) {
            mainViewModel.onStartFirstScreen()
        }
    }

    override fun onDestroy() {
        mainViewModel.onDetachActivity()
        super.onDestroy()
    }
}