package com.whacamole.view

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.whacamole.R
import com.whacamole.databinding.FragmentGameBinding
import com.whacamole.vm.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mp = MediaPlayer.create(requireContext(), R.raw.button_click);

        viewModel.cancelTimers()

        binding.gridLayout.forEach {
            it.background = ContextCompat.getDrawable(requireContext(), R.drawable.hole)
            it.setOnClickListener {
                if (it.tag == viewModel.molePosition.value.toString())
                    viewModel.incScore()
            }
        }

        viewModel.currentScore.observe(viewLifecycleOwner) {
            binding.score.text = "Your score: $it"
        }

        viewModel.molePosition.observe(viewLifecycleOwner) {
            binding.gridLayout.forEach { it.background = ContextCompat.getDrawable(requireContext(), R.drawable.hole) }
            if (it > -1) {
                binding.gridLayout[it].background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.mole)
            }
        }

        viewModel.timeLeft.observe(viewLifecycleOwner) {
            binding.timeLeft.text = "Time left: $it"
            if (it == 0) {
                endGame()
            }
        }

        binding.buttonStart.setOnClickListener {
            mp.start()
            if (binding.buttonStart.text == resources.getString(R.string.start_button_text)) {
                viewModel.startGameTimer()
                viewModel.startMoleTimer()
                binding.buttonStart.text = resources.getString(R.string.stop_button_text)
            } else {
                endGame()
            }
        }
    }

    private fun endGame() {
        viewModel.saveRecord()
        val action = GameFragmentDirections.actionGameFragmentToEndFragment()
        findNavController().navigate(action)
    }
}