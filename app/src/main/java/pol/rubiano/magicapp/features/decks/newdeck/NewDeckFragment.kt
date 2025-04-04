package pol.rubiano.magicapp.features.decks.newdeck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.NewDeckFragmentBinding
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.decks.DecksViewModel
import java.util.UUID

class NewDeckFragment : Fragment() {

    private var _binding: NewDeckFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()
//    private val args: NewDeckFragmentDirections by navArgs()

//    private lateinit var newDeck: Deck

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewDeckFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.addedDeck.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> { }
                is UiState.Success -> {
                    val deck = state.data
                    val action = NewDeckFragmentDirections.actNewDeckToDeckDetails(deck)
                    findNavController().navigate(action)
                }
                is UiState.Error -> { AppError.AppDataError }
                is UiState.Empty -> {}
            }
        }
    }

    fun newDeck() {
        val name = binding.newDeckName.text.toString().trim()
        if (name.isEmpty()) { // TODO - REVISAR SI IMPLEMENTO
            binding.newDeckName.error = "Name required"
            return
        }
        val description = binding.newDeckDescription.text.toString().trim()
        if (name.isNotEmpty()) {
            val newDeck = Deck(
                id = UUID.randomUUID().toString(),
                name = name,
                description = description,
                colors = emptyList(),
                cardIds = emptyList(),
                sideBoard = emptyList(),
                maybeBoard = emptyList()
            )
            viewModel.addDeck(newDeck)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

