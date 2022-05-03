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

        // Эттачим активити к активити холдеру
        mainViewModel.onAttachActivity(this)

        // Вызываем метод вьюмодели для отображения стартового фрагмента только при первом onCreate,
        // чтоб при смене конфигурации (перевороте экрана, смене темы) он не вызывался
        if (savedInstanceState == null) {
            mainViewModel.onStartFirstScreen()
        }
    }

    override fun onDestroy() {
        // Детачим активити от активити холдера, чтоб избежать утечек памяти
        mainViewModel.onDetachActivity()
        super.onDestroy()
    }
}