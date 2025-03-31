package pol.rubiano.magicapp.features.presentation.ui.decks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.databinding.DeckFragmentEditDeckBinding
import pol.rubiano.magicapp.features.domain.entities.Deck
import pol.rubiano.magicapp.features.presentation.viewmodels.DecksViewModel

class EditDeckFragment : Fragment() {

    private var _binding: DeckFragmentEditDeckBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()
    private val args: EditDeckFragmentArgs by navArgs()

    private lateinit var currentDeck: Deck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeckFragmentEditDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deckId = args.deckId
        viewModel.loadDeckById(deckId)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.selectedDeck.observe(viewLifecycleOwner) { deck ->
            deck?.let {
                currentDeck = it
                binding.editDeckName.setText(it.name)
                binding.editDeckDescription.setText(it.description)
            }
        }
    }

    fun saveDeckChanges() {
        val updatedDeck = currentDeck.copy(
            name = binding.editDeckName.text.toString(),
            description = binding.editDeckDescription.text.toString()
        )
        viewModel.updateDeck(updatedDeck)

        findNavController().navigate(
            EditDeckFragmentDirections.actionDeckEditFragmentToDecksFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}