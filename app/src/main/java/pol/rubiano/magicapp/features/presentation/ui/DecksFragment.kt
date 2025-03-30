package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.DecksFragmentBinding
import pol.rubiano.magicapp.features.presentation.adapters.DecksAdapter
import pol.rubiano.magicapp.features.presentation.viewmodels.DeckViewModel

class DecksFragment : Fragment() {

    private var _binding: DecksFragmentBinding? = null
    private val binding get() = _binding!!
    private val deckViewModel: DeckViewModel by viewModel()

    private lateinit var decksAdapter: DecksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DecksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decksAdapter = DecksAdapter(onItemClicked = { deck ->
            findNavController().navigate(DecksFragmentDirections.actionDecksFragmentToDeckConfigFragment(deck.id))
        })

        binding.decksRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = decksAdapter
        }

        deckViewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.decks?.let { decks ->
                if (decks.isEmpty()) {
                    // No decks exist, navigate to NewDeckFragment.
                    findNavController().navigate(R.id.action_decks_fragment_to_new_deck_fragment)
                } else {
                    decksAdapter.updateDecks(decks)
                }
            }
        }
        childFragmentManager.setFragmentResultListener("new_deck_created", viewLifecycleOwner) { _, _ ->
            deckViewModel.loadDecks()
        }

    }

    override fun onResume() {
        super.onResume()
        deckViewModel.loadDecks()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

