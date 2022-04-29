package com.degalex.waadsutest.ui.screens.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.degalex.waadsutest.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment private constructor() : Fragment() {

    private lateinit var binding: FragmentStartBinding

    private val startViewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.openMapButton.setOnClickListener {
            startViewModel.onOpenMapClicked()
        }
    }

    companion object {

        fun newInstance(): StartFragment {
            return StartFragment()
        }
    }
}