package com.whacamole.view

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.whacamole.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mp = MediaPlayer.create(requireContext(), R.raw.button_click)

        binding.buttonMenu.setOnClickListener {
            mp.start()
            val action = EndFragmentDirections.actionEndFragmentToMenuFragment()
            findNavController().navigate(action)
        }

        binding.buttonPlayAgain.setOnClickListener {
            mp.start()
            val action = EndFragmentDirections.actionEndFragmentToGameFragment()
            findNavController().navigate(action)
        }

        viewModel.currentScore.observe(viewLifecycleOwner) {
            binding.scoreText.text = "Your score: $it"
        }

        viewModel.recordScore.observe(viewLifecycleOwner) {
            binding.recordText.text = "Your record: $it"
        }
    }


}