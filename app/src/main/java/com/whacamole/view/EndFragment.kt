package com.whacamole.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.whacamole.databinding.FragmentEndBinding
import com.whacamole.databinding.FragmentMenuBinding
import com.whacamole.vm.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EndFragment : Fragment() {

    private lateinit var binding: FragmentEndBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndBinding.inflate(inflater, container, false)
        return binding.root
    }


}