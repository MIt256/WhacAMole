package com.whacamole.view

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.whacamole.R
import com.whacamole.databinding.FragmentMenuBinding
import com.whacamole.vm.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mp = MediaPlayer.create(requireContext(), R.raw.button_click)

        binding.buttonPlay.setOnClickListener {
            mp.start()
            val action = MenuFragmentDirections.actionMenuFragmentToGameFragment()
            findNavController().navigate(action)
        }

        viewModel.recordScore.observe(viewLifecycleOwner) {
            binding.recordText.text = "Your record: $it"
        }


    }

}