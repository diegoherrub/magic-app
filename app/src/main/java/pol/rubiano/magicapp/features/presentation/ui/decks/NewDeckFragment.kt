package pol.rubiano.magicapp.features.presentation.ui.decks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.DeckFragmentNewDeckBinding
import pol.rubiano.magicapp.features.domain.entities.Deck
import pol.rubiano.magicapp.features.presentation.viewmodels.DeckViewModel
import java.util.UUID

class NewDeckFragment : Fragment() {

    private var _binding: DeckFragmentNewDeckBinding? = null
    private val binding get() = _binding!!

    private val deckViewModel: DeckViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeckFragmentNewDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveDeckButton.setOnClickListener {
            val name = binding.deckNameInput.text.toString().trim()
            val description = binding.deckDescriptionInput.text.toString().trim()
            if (name.isNotEmpty()) {
                // Create a new deck. Here we generate a random ID and set the current date.
                val newDeck = Deck(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    description = description,
                    cardIds = emptyList(),
                    sideBoard = emptyList(),
                    maybeBoard = emptyList()
                )

                lifecycleScope.launch {
                    deckViewModel.addDeck(newDeck)
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                    delay(1000)
                    parentFragmentManager.setFragmentResult("new_deck_created", Bundle())
                    findNavController().navigate(R.id.action_new_deck_fragment_to_decks_fragment)
                }
            } else {
                // Optionally notify the user to input a valid name.
                binding.deckNameInput.error = "Please enter a deck name"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
