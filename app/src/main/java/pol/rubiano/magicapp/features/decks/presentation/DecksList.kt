package pol.rubiano.magicapp.features.decks.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.DecksListBinding
import pol.rubiano.magicapp.features.decks.presentation.assets.DecksAdapter
import pol.rubiano.magicapp.features.decks.presentation.assets.DecksViewModel

class DecksList : Fragment() {

    private var _binding: DecksListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()
    private val adapter = DecksAdapter { deck ->
        val action = DecksListDirections.actFromDecksListToDeckPanel(deck.id)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DecksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupObserver()
    }

    private fun setupToolbar() {
        binding.decksList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DecksList.adapter
        }
        viewModel.loadUserDecks()
    }

    private fun setupObserver() {
        viewModel.userDecks.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    val decks = state.data
                    adapter.submitList(decks)
                }

                is UiState.Empty -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.str_noDecksYet,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is UiState.Error -> {
                    AppError.AppDataError
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

